import java.io.File
import java.lang.Double.POSITIVE_INFINITY
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized

var RAYS = 0
var HITS = 0

@OptIn(InternalCoroutinesApi::class)
fun main(args: Array<String>) {

    val aspectRatio = 16.0 / 9.0
    val imageHeight = 1080
    val imageWidth = (imageHeight * aspectRatio).toInt()

    // Camera Requirement
    val lookFrom = MyVector(2.0, 2.0, 5.0)
    val lookAt = MyVector(0.0, 0.0, 0.0)
    val vUp = MyVector(0.0, 1.0, 0.0)
    val vFov = 90.0
    val aperture = 0.1
    val distToFocus = lookFrom.sub(lookAt).length()

    val cam = Camera(lookFrom, lookAt, vUp, vFov, aspectRatio, aperture, distToFocus)

    // Output Requirement
    val fn = writeFile("output1.ppm", "P3", imageWidth, imageHeight, 255)

    var world = HittableList()

    if (!args.isNullOrEmpty()) {
        val tm = TriangleMesh(args[0])
        val triangles = tm.getTriangles()
        for (t in triangles) {
            world.add(t)
        }
    }

    val m1 = Metal(MyVector(0.5, 0.2, 0.1))
    world.add(Sphere(MyVector(1.0, 1.0, 0.0), 0.7, m1))
    val m2 = Lambertian(MyVector(0.5, 0.0, 0.0))
    world.add(Sphere(MyVector(2.0, 2.0, 0.0), 1.0, m2))
    val m3 = Metal(MyVector(0.2, 0.8, 0.0))
    world.add(Sphere(MyVector(-2.0, -2.0, 0.0), 1.4, m3))
    val m4 = Metal(MyVector(0.8, 0.8, 0.8))
    world.add(Sphere(MyVector(-20.0, -2.0, 0.0), 6.0, m4))
    val m5 = Lambertian(MyVector(0.8, 0.0, 0.8))
    world.add(Sphere(MyVector(5.0, -2.0, -4.0), 4.0, m5))
    val m6 = Metal(MyVector(0.8, 0.0, 0.8))
    world.add(Sphere(MyVector(0.0, -7.0, -25.0), 20.0, m6))

    val samplesPerPixel = 10
    val maxDepth = 5

    // Coroutine setup
    var pixels = arrayOfNulls<MyVector>(imageWidth * imageHeight)

    var pixelNum = 0
    fun getPixelNumLock(): Int {
        synchronized(pixelNum) {
            return pixelNum++
        }
    }

    val TIME = measureTimeMillis {
        runBlocking {
            for (threadNum in 0 until 1) {
                launch {
                    withContext(Dispatchers.Default) {
                        while (true) {
                            // get next pixel lock
                            var myPixNum = getPixelNumLock()
                            if (myPixNum >= imageWidth * imageHeight) {
                                break
                            } else {
                                var pixelColor = MyVector(0.0, 0.0, 0.0)
                                var widthPixel = myPixNum % imageWidth
                                if (pixelNum % imageWidth == 0) {
                                    widthPixel = myPixNum % imageWidth + imageWidth
                                }
                                val heightPixel = (myPixNum - widthPixel) / imageWidth + 1

                                for (s in 1..samplesPerPixel) {  // Anti-aliasing
                                    val u = (widthPixel.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                                    val v = (heightPixel.toDouble() + Random.nextDouble()) / (imageHeight - 1)

                                    val r = cam.getRay(u, v)
                                    val color = rayColor(r, world, maxDepth)
                                    pixelColor = pixelColor.add(color)
                                }
                                pixels[myPixNum] = pixelColor // 0-indexed
                            }
                        }
                    }
                }
            }
        }
    }
    for (p in pixels) {
        if (p != null) {
            writeColor(fn, p, samplesPerPixel)
        }
    }
    println(TIME)
}

fun rayColor(r: MyRay, world: HittableList, depth: Int): MyVector {
    /**
     * Returns color of rays hit in world. Returns a vector of RGB values [0.0, 1.0] for a single pixel.
     */

    if (depth <= 0) {
        return MyVector(0.0, 0.0, 0.0)
    }

    val hitRec = world.hit(r, 0.01, POSITIVE_INFINITY)
    if (hitRec != null) {
        HITS++

        val scattered = MyRay(MyVector(0.0, 0.0, 0.0), MyVector(0.0, 0.0, 0.0))
        val attenuation = MyVector(0.0, 0.0, 0.0)

        val scatterResult = Material.ScatterResult(attenuation, scattered)
        if (hitRec.mat.scatter(r, hitRec, scatterResult)) {
            return scatterResult.attenuation.pairMultiply(rayColor(scatterResult.scattered, world, depth - 1))
        }
        return MyVector(0.0, 0.0, 0.0)
    }

    val unitDirection = r.direction.unit()
    val t = 0.5 * (unitDirection.y + 1.0)
    return MyVector(1.0, 1.0, 1.0)
        .multiply(1.0 - t)
        .add(
            MyVector(.5, 0.7, 1.0)
                .multiply(t)
        )
}

fun writeColor(fn: File, pixel: MyVector, samplesPerPixel: Int) {
    /**
     * Writes .ppm color to fn. Maps pixel RGB [0.0, 1.0] -> [0, 255].
     */
    var r = pixel.x
    var g = pixel.y
    var b = pixel.z

    // Gamma Correction Requirement
    var scale = 1.0 / samplesPerPixel
    r = sqrt(r * scale)
    g = sqrt(g * scale)
    b = sqrt(b * scale)

    r *= 256 * clamp(r, 0.0, 0.999)
    g *= 256 * clamp(g, 0.0, 0.999)
    b *= 256 * clamp(b, 0.0, 0.999)

    val type = "P3"
    if (type == "P3") {
        fn.appendText("${r.toInt()}\n${g.toInt()}\n${b.toInt()}\n")
    }
}

fun writeFile(fn: String, type: String, x: Int, y: Int, maxColor: Int): File {
    /**
     * Writes .ppm metadata to filename fn. Returns file handle.
     */
    // filename, type (P3 / P6), dimensions, max color
    val meta = "$type\n$x $y\n$maxColor\n"
    val file = File(fn)
    file.writeText(meta)
    return file
}