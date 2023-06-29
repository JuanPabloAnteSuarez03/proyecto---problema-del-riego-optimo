import riego._
import org.scalameter._
package object Benchmark {
    type AlgoritmoRiego = (Finca, Distancia) => (ProgRiego, Int)
    type AlgoritmoCostoRiego = (Finca, ProgRiego) => Int
    type AlgoritmoMovilidad = (Finca, ProgRiego, Distancia) => Int
    type AlgoritmoProgramaciones = Finca => Vector[ProgRiego]

    def compararAlgoritmos(a1:AlgoritmoRiego, a2:AlgoritmoRiego)
                        (f:Finca, d:Distancia):(Double,Double, Double) = {
    val timeA1 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure (a1(f,d))

    val timeA2 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer(new Warmer.Default) measure (a2(f,d))

    val speedUp= timeA1.value/timeA2.value
    (timeA1.value, timeA2.value, speedUp)
    }

    def compararAlgoritmosCostoRIego(a1: AlgoritmoCostoRiego, a2: AlgoritmoCostoRiego)(f: Finca, pi: ProgRiego): (Double, Double, Double) = {
    val timeA1 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a1(f, pi))

    val timeA2 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a2(f, pi))

    val speedUp = timeA1.value / timeA2.value
    (timeA1.value, timeA2.value, speedUp)
    }

    def compararAlgoritmosMovilidad(a1: AlgoritmoMovilidad, a2: AlgoritmoMovilidad)(f: Finca, pi: ProgRiego, d: Distancia): (Double, Double, Double) = {
    val timeA1 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a1(f, pi, d))

    val timeA2 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a2(f, pi, d))

    val speedUp = timeA1.value / timeA2.value
    (timeA1.value, timeA2.value, speedUp)
    }

    def compararAlgoritmosProgramaciones(a1: AlgoritmoProgramaciones, a2: AlgoritmoProgramaciones)(f: Finca): (Double, Double, Double) = {
    val timeA1 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a1(f))

    val timeA2 = config(
        KeyValue(Key.exec.minWarmupRuns -> 20),
        KeyValue(Key.exec.maxWarmupRuns -> 60),
        KeyValue(Key.verbose -> false)
    ) withWarmer (new Warmer.Default) measure (a2(f))

    val speedUp = timeA1.value / timeA2.value
    (timeA1.value, timeA2.value, speedUp)
    }
}