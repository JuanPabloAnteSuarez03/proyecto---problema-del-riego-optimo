package object riego{
  import scala.util.Random

  // Un tablon es una tripleta con el tiempo de supervivencia,
  // el tiempo de riego y la prioridad del tablon
  type Tablon = (Int, Int, Int)

  // Una finca es un vector de tablones
  type Finca = Vector[Tablon]
  // si f: Finca, f(i) = (tsi, tri, pi)

  // La distancia entre dos tablones se representa por
  // una matriz
  type Distancia = Vector[Vector[Int]]

  // Una programacion de riego es un vector que asocia
  // cada tablon con su turno de riego (0 es el primer turno,
  // n−1 es el ultimo turno)
  type ProgRiego = Vector[Int]
  // si v: ProgRiego, y v.length == n, v es una permutacion
  // de {0, ..., n−1} v(i) es el turno de riego del tablon i
  // para 0 <= i < n

  // El tiempo de inicio de riego es un vector que asocia
  // cada tablon con el momento del tiempo en que se riega
  type TiempoInicioRiego = Vector[Int]
  // si t: TiempoInicioRiego y t.length == n, t(i) es la hora a
  // la que inicia a regarse el tablon i

  //Funciones usadas para generar entradas al azar
  val random = new Random()

  def fincaAlAzar(long: Int): Finca = {
    // Crea una finca de long tablones,
    // con valores aleatorios entre 1 y long*2 para el tiempo
    // de supervivencia, entre 1 y long para el tiempo
    // de riego, y entre 1 y 4 para la prioridad
    val v = Vector.fill(long) {
      (random.nextInt(long * 2) + 1,
      random.nextInt(long) + 1,
      random.nextInt(4) + 1)
    }
    v
  }

  def distanciaAlAzar(long: Int): Distancia = {
    // Crea una matriz de distancias para una finca
    // de long tablones, con valores aleatorios entre
    // 1 y long*3
    val v = Vector.fill(long, long) {
      random.nextInt(long * 3) + 1
    }
    Vector.tabulate(long, long) { (i, j) =>
      if (i < j) v(i)(j)
      else if (i == j) 0
      else v(j)(i)
    }
  }

  //Funciones usadas para explorar las entradas

  def tSup(f: Finca, i: Int): Int = {
    f(i)._1
  }

  def tReg(f: Finca, i: Int): Int = {
    f(i)._2
  }

  def prio(f: Finca, i: Int): Int = {
    f(i)._3
  }

  //Funciones 2.3.1 Calculando el tiempo de inicio de riego
  def tIR(f: Finca, pi: ProgRiego): TiempoInicioRiego = {
    // Dada una finca f y una programación de riego pi,
    // y f.length == n, tIR(f, pi) devuelve t: TiempoInicioRiego
    // tal que t(i) es el tiempo en que inicia el riego del
    // tablón i de la finca f según pi
    
  }

  //Funciones 2.3.2 Calculando costos
  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {
    // devuelve el costo de regar el tablón i de la finca f
    // con la programación pi
    
  }

  def costoRiegoFinca(f: Finca, pi: ProgRiego): Int = {
    // devuelve el costo total de regar una finca f dada
    // una programación de riego pi
    
  }

  def costoMovilidad(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    // ...
    
  }

  //Funciones 2.3.3 Generando programaciones de riego
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
  // Dada una finca de n tablones, devuelve todas las
  // posibles programaciones de riego de la finca
  
  }

  //Funciones 2.3.4 Calculando una programacion de riego optimo
  def ProgramacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
  // Dada una finca, devuelve la programación de riego óptima
  
  }

}