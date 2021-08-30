class HittableList(): Hittable() {

    var objects = ArrayList<Hittable>()

    constructor (h: Hittable) : this() {
        add(h)
    }

    fun clear() {
        objects.clear()
    }

    fun add(h: Hittable){
        objects.add(h)
    }

    override fun hit(r: MyRay, tMin: Double, tMax: Double): HitRecord? {
        var closestSoFar = tMax
        var rec: HitRecord? = null
        for (obj in objects){
            val tempRec = obj.hit(r, tMin, closestSoFar)
            if (tempRec != null) {
                closestSoFar = tempRec.t
                rec = tempRec
            }
        }
        return rec
    }
}