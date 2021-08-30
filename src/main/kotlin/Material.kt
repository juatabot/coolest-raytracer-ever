import javolution.io.Struct

abstract class Material {
    abstract fun scatter(rIn: MyRay, hitRec: HitRecord, scatterResult: ScatterResult): Boolean

//    abstract fun scatter(rIn: MyRay, hitRec: HitRecord): ScatterResult

    data class ScatterResult(var attenuation: MyVector, var scattered: MyRay)
}