import riego._
def costoMovilidad1(f: Finca, pi: ProgRiego, d: Distancia): Int = {
  var totalCost = 0

  for (j <- 0 until f.length - 1) {
    val tablonActual = pi(j)
    val tablonSiguiente = pi(j + 1)
    val costoMovimiento = d(tablonActual)(tablonSiguiente)
    totalCost += costoMovimiento
  }

  totalCost
}
val finca: Finca = Vector(
  (10, 3, 4),
  (5, 3, 3), 
  (2, 2, 1),
  (8, 1, 1),
  (6, 4, 2) 
)

val distancia: Distancia = Vector(
  Vector(0, 2, 2, 4, 4),
  Vector(2, 0, 4, 2, 6),
  Vector(2, 4, 0, 2, 2),
  Vector(4, 2, 2, 0, 4),
  Vector(4, 6, 2, 4, 0)
)

val programacion1: ProgRiego = Vector(0, 1, 4, 2, 3)
tIR(finca, programacion1)
costoRiegoTablon(1, finca, programacion1)
costoRiegoFinca(finca, programacion1)
costoMovilidad(finca, programacion1, distancia)