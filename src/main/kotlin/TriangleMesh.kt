import kotlin.random.Random

class TriangleMesh (private val filename: String) {
    val parser = ObjParser(filename)
    private var triangles = ArrayList<Triangle>()

    init {
        val faceVertexIndices = parser.getVertexIndex().chunked(3)
        val vertices = parser.getVertices()

        for (face in faceVertexIndices){
            val vertexVectors = ArrayList<MyVector>()
            for (vertexIndex in face) {
                vertexVectors.add(getVertexVector(vertexIndex - 1, vertices))
            }
            val tri = Triangle(vertexVectors, Metal(MyVector(0.1, 0.1, 0.5)))
            triangles.add(tri)
        }
    }

    fun getTriangles() : ArrayList<Triangle> {
        return triangles
    }

    private fun getVertexVector(vertexIndex: Int, vertices: List<String>) : MyVector {
        val values = vertices[vertexIndex].split(" ")
        return MyVector(values[1].toDouble(), values[2].toDouble(), values[3].toDouble())
    }
}