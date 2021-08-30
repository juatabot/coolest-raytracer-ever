class Lambertian(var albedo: MyVector): Material(){

    override fun scatter(rIn: MyRay, hitRec: HitRecord, scatterResult: ScatterResult) : Boolean {
        var scatterDirection = hitRec.normal.add(MyVector.randomUnitVector())
        if (scatterDirection.nearZero()){
            scatterDirection = hitRec.normal
        }
        scatterResult.attenuation = albedo
        scatterResult.scattered = MyRay(hitRec.p, scatterDirection)
        return true
    }
}