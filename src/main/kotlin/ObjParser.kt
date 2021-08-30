import java.io.File

class ObjParser(private val filename: String) {

    val f = File(filename)
    val lines = f.readLines()

    fun getVertices() : List<String> {
        val vertices = lines.filter{it.startsWith("v ")}
        return vertices
    }

    fun getNumFaces() : Int {
        val faces = lines.filter{it.startsWith("f")}.size
        return faces
    }

    fun getFaceIndexArray() : ArrayList<Int> {
        var arr = ArrayList<Int>()
        for (face in lines.filter{it.startsWith("f")}){
            arr.add(face.split(" ").size - 1)
        }
        return arr
    }

    fun getVertexIndex() : ArrayList<Int> {
        var arr = ArrayList<Int>()
        for (face in lines.filter{it.startsWith("f")}){
            var vertices = face.split(" ").filter { !it.startsWith("f") }
            vertices = vertices.map{it.split("//")[0]}
            for (i in vertices){
                arr.add(i.toInt())
            }
        }
        return arr
    }

    fun getVertexNormals(): List<String> {
        val vertexNormals = lines.filter{it.startsWith("vn")}
        return vertexNormals
    }
}