package baekjoon.gold

/**
 * 백준 1041번 문제 - 주사위(골드 5)
 * 수학, 구현, 그리디
 */
fun main1041() = with(System.`in`.bufferedReader() to System.out.bufferedWriter()) {
    val (reader, writer) = this

    val size = reader.readLine().toLong()
    val faces = reader.readLine().split(" ").map { it.toLong() }

    val dice = Dice(faces)

    if (size <= 0) {
        writer.write(0)
    } else if (size == 1L) {
        writer.write(dice.getMinimumOfFiveFaces().toString())
    } else {
        val threeFaceDiceAmount = 4
        val twoFaceDiceAmount = (8 * size) - 12
        val oneFaceDiceAmount = 5 * (size * size) - (16 * size) + 12

        val totalOfThreeFaces = dice.getMinimumOfThreeFaces() * threeFaceDiceAmount
        val totalOfTwoFace = dice.getMinimumOfTwoFace() * twoFaceDiceAmount
        val totalOfOneFace = dice.getMinimumOfOneFace() * oneFaceDiceAmount

        writer.write((totalOfThreeFaces + totalOfTwoFace + totalOfOneFace).toString())
    }

    writer.close()
}

private class Dice(
    faceValues: List<Long>
) {
    private val faces = mutableListOf<Face>()

    private var a: Face = Face(faceValues[0])
    private var b: Face = Face(faceValues[1])
    private var c: Face = Face(faceValues[2])
    private var d: Face = Face(faceValues[3])
    private var e: Face = Face(faceValues[4])
    private var f: Face = Face(faceValues[5])

    init {
        a.opposite = f
        f.opposite = a
        b.opposite = e
        e.opposite = b
        c.opposite = d
        d.opposite = c

        faces.addAll(listOf(a, b, c, d, e, f))
    }

    fun getMinimumOfFiveFaces(): Long {
        val total = faces.sumOf { it.value }
        val max = faces.maxBy { it.value }.value

        return total - max
    }

    fun getMinimumOfThreeFaces(): Long {
        val faceSets: MutableList<Set<Face>> = mutableListOf()

        for (i in 0..< faces.size - 2) {
            for (j in i + 1 ..< faces.size - 1) {
                for (k in j + 1..< faces.size) {
                    faceSets.add(setOf(faces[i], faces[j], faces[k]))
                }
            }
        }

        return faceSets.filter { everyFacesAdjacent(it) }
            .minOf { faces -> faces.sumOf { it.value } }
    }

    fun getMinimumOfTwoFace(): Long {
        val minimumA = getFacesWithoutOpposite(a)
            .minOf { it.value + a.value }
        val minimumB = getFacesWithoutOpposite(b)
            .minOf { it.value + b.value }
        val minimumC = getFacesWithoutOpposite(c)
            .minOf { it.value + c.value }
        val minimumD = getFacesWithoutOpposite(d)
            .minOf { it.value + d.value }
        val minimumE = getFacesWithoutOpposite(e)
            .minOf { it.value + e.value }
        val minimumF = getFacesWithoutOpposite(f)
            .minOf { it.value + f.value }

        return min(minimumA, minimumB, minimumC, minimumD, minimumE, minimumF)
    }

    fun getMinimumOfOneFace(): Long {
        return min(a.value, b.value, c.value, d.value, e.value, f.value)
    }

    private fun getFacesWithoutOpposite(face: Face): List<Face> {
        return faces.filter { it !== face && !it.isOpposite(face) }
    }

    private fun everyFacesAdjacent(faces: Set<Face>): Boolean {
        for (face in faces) {
            if (faces.any { it.isOpposite(face) }) return false
        }

        return true
    }
}

private class Face(
    val value: Long
) {
    lateinit var opposite: Face

    fun isOpposite(other: Face): Boolean {
        return opposite === other
    }
}

fun min(vararg numbers: Long): Long {
    return numbers.min()
}
