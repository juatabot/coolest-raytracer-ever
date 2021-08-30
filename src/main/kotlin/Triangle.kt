import kotlin.math.abs

class Triangle (val vertices: ArrayList<MyVector>,
                val mat: Material) : Hittable() {

    override fun hit(r: MyRay, tMin: Double, tMax: Double): HitRecord? {

        //https://courses.cs.washington.edu/courses/csep557/14au/lectures/triangle_intersection.pdf
        //https://www.lighthouse3d.com/tutorials/maths/ray-triangle-intersection/
        val EPSILON = 0.0000001f

        val v0 = vertices[0]
        val v1 = vertices[1]
        val v2 = vertices[2]

        // Compute the plane's normal
        val v0v1 = v1.sub(v0)
        val v0v2 = v2.sub(v0)
        val normal = v0v1.cross(v0v2).unit()

        // Check if the ray and plane are parallel
        val normalDotRayDirection = normal.dot(r.direction)
        if (abs(normalDotRayDirection) < EPSILON) {
            return null
        }

        // check if the triangle is facing away from the ray
        if (normalDotRayDirection > 0) {
            return null
        }

        // The equation of a plane is ax + by + cz + d = 0
        // Compute the d parameter
        val d = - normal.dot(v0)

        // The distance from the origin to where the ray hits the plane
        val t = -(normal.dot(r.origin) + d) / normalDotRayDirection
        // Check if the triangle is behind the ray
        if (t < 0) {
            return null
        }

        // Compute the intersection point
        val hitPos = r.origin.add(r.direction.multiply(t))

        // Inside/outside test

        // Edge 0
        val edge0 = v1.sub(v0)
        val vp0 = hitPos.sub(v0)
        var C = edge0.cross(vp0)
        if (normal.dot(C) < 0) {
            return null
        }

        // Edge 1
        val edge1 = v2.sub(v1)
        val vp1 = hitPos.sub(v1)
        C = edge1.cross(vp1)
        if (normal.dot(C) < 0) {
            return null
        }

        // Edge 2
        val edge2 = v0.sub(v2)
        val vp2 = hitPos.sub(v2)
        C = edge2.cross(vp2)
        if (normal.dot(C) < 0) {
            return null
        }

        val rec = HitRecord(hitPos, normal.unit(), t)
//        rec.setFaceNormal(r, normal.multiply(-1.0))
        rec.mat = mat
        return rec
    }

    @Override
    override fun toString(): String {
        return "$vertices"
    }
}