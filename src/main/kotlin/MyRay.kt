class MyRay(val origin: MyVector, val direction: MyVector) {
    fun at(t: Double): MyVector{
        return MyVector(
            origin.x + t * direction.x,
            origin.y + t * direction.y,
            origin.z + t * direction.z
        )
    }
}