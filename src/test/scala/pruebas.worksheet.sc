import riego._
import org.scalameter._
import Benchmark._

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
costoMovilidadPar(finca,programacion1, distancia)
generarProgramacionesRiego(finca).length
ProgramacionRiegoOptimo(finca, distancia)
val fincaAlAzar1 = fincaAlAzar(5)
val distanciaAlAzar1 = distanciaAlAzar(5)
generarProgramacionesRiego(fincaAlAzar1).length
ProgramacionRiegoOptimo(finca, distancia)
ProgramacionRiegoOptimoPar(finca, distancia)
ProgramacionRiegoOptimo(fincaAlAzar1, distanciaAlAzar1)
ProgramacionRiegoOptimoPar(fincaAlAzar1, distanciaAlAzar1)
costoRiegoFincaPar(finca,programacion1)
generarProgramacionesRiego(finca)
generarProgramacionesRiegoPar(finca)


// Comparativa entre costoRiegoFinca y costoRiegoFincaPar
for(i <- 50 to 80; 
    f = fincaAlAzar(i); 
    p = generarProgramacionRiego(i)) yield (compararAlgoritmosCostoRIego(costoRiegoFinca, costoRiegoFincaPar)(f,p))
// Comparativa entre costoMovilidad y costoMovilidadPar
for(i <- 570 to 600; 
    f = fincaAlAzar(i); 
    p = generarProgramacionRiego(i);
    d = distanciaAlAzar(i)) yield (compararAlgoritmosMovilidad(costoMovilidad, costoMovilidadPar)(f,p,d))
// Comparativa entre generarProgramacionesRiego y generarProgramacionesRiegoPar
for(i <- 0 to 10; 
    f = fincaAlAzar(i)) yield (compararAlgoritmosProgramaciones(generarProgramacionesRiego, generarProgramacionesRiegoPar)(f))
// Comparativa entre ProgramacionRiegoOptimo y ProgramacionRiegoOptimoPar
for(i <- 0 to 10; 
    f = fincaAlAzar(i); 
    d = distanciaAlAzar(i)) yield (compararAlgoritmos(ProgramacionRiegoOptimo, ProgramacionRiegoOptimoPar)(f,d))

