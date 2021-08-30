class Metal(var albedo: MyVector) : Material() {

    var fuzz = 0.0


//    constructor(a: MyVector, f: Double) : this() {
//        albedo = a
//        if (f < 1.0) {
//            fuzz = f
//        }
//        else{
//            fuzz = 1.0
//        }
//    }

    override fun scatter(rIn: MyRay, hitRec: HitRecord, scatterResult: ScatterResult): Boolean {
        val reflected = MyVector.reflect(rIn.direction.unit(), hitRec.normal)
        scatterResult.scattered = MyRay(hitRec.p, reflected.add(MyVector.randomInUnitSphere().multiply(fuzz)))
//        scatterResult.scattered = MyRay(hitRec.p, reflected)
        scatterResult.attenuation = albedo
        return scatterResult.scattered.direction.dot(hitRec.normal) > 0.0
    }

}