import kotlin.math.sqrt

class Sphere(val center: MyVector, val radius: Double, val mat: Material) :Hittable(){

    override fun hit(r: MyRay, tMin: Double, tMax: Double): HitRecord? {
        val oc = r.origin.sub(center)
        val a = r.direction.length() * r.direction.length()
        val halfB = oc.dot(r.direction)
        val c = oc.length() * oc.length() - radius * radius
        val discriminant = halfB * halfB - a * c
        if (discriminant < 0) {
            return null
        }
        val sqrtd = sqrt(discriminant)
        var root = (-halfB - sqrtd) / a

        if (root < tMin || tMax < root){
            root = (-halfB + sqrtd) / a
            if (root < tMin || tMax < root){
                return null
            }
        }

        val rec = HitRecord()
        rec.t = root
        rec.p = r.at(rec.t)
        val outwardNormal = rec.p.sub(center).divide(radius)
        rec.setFaceNormal(r, outwardNormal)
        rec.mat = mat

        return rec
    }
}