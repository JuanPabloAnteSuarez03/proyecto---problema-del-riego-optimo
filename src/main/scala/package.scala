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
    def tIRAux(j: Int, acc: Vector[Int]): Vector[Int] = {
      if (j >= f.length) acc
      else {
        val tIRj = if (j == 0) 0 else acc(pi(j - 1)) + tReg(f, pi(j - 1))
        tIRAux(j + 1, acc.updated(pi(j), tIRj))
      }
    }

    tIRAux(0, Vector.fill(f.length)(0))
  }

  //Funciones 2.3.2 Calculando costos
  def costoRiegoTablon(i: Int, f: Finca, pi: ProgRiego): Int = {
    val tsFi = tSup(f, i)//10
    val trFi = tReg(f, i)//3
    val pFi = prio(f, i)//4

    if (tsFi - trFi >= tIR(f, pi)(i)) {
      tsFi - (tIR(f, pi)(i) + trFi)
    } else {
      pFi * ((tIR(f, pi)(i) + trFi) - tsFi)
    }
  }

  def costoRiegoFinca(f: Finca, pi: ProgRiego): Int = {
    val totalCost = (0 until f.length).foldLeft(0) { (acc, i) =>
      acc + costoRiegoTablon(i, f, pi)
    }
    totalCost
  }

  def costoMovilidad(f: Finca, pi: ProgRiego, d: Distancia): Int = {
    val movimientos = if (pi.length > 1) pi.sliding(2).toVector else Vector.empty
    val costosMovimiento = movimientos.map { case Vector(t1, t2) => d(t1)(t2) }
    costosMovimiento.sum
  }


  //Funciones 2.3.3 Generando programaciones de riego
  def generarProgramacionesRiego(f: Finca): Vector[ProgRiego] = {
    def generarProgramacionesRecursivo(progActual: ProgRiego, restantes: Vector[Int]): Vector[ProgRiego] = {
      if (restantes.isEmpty) {
        Vector(progActual)
      } else {
        val indiceActual = f.length - restantes.length 
        val indiceRestantes = restantes.indices.toVector 

        indiceRestantes.flatMap { indice =>
          val nuevaProg = progActual.updated(indiceActual, restantes(indice)) 
          val nuevosRestantes = restantes.patch(indice, Nil, 1) 
          generarProgramacionesRecursivo(nuevaProg, nuevosRestantes) 
        }
      }
    }

    val progInicial = Vector.fill(f.length)(0) 
    val tablonesRestantes = (0 until f.length).toVector 

    generarProgramacionesRecursivo(progInicial, tablonesRestantes)
  }

  //Se añade alternativa sin usar For o Var, ya que no se consideran propios del paradigma
  def ProgramacionRiegoOptimo(f: Finca, d: Distancia): (ProgRiego, Int) = {
    val programaciones = generarProgramacionesRiego(f)
    
    val (costoMinimo, programacionOptima) = programaciones.foldLeft((Int.MaxValue, Vector.empty[Int])) {
      case ((minCost, optProg), prog) =>
        val costoRiego = costoRiegoFinca(f, prog)
        val costoMovilidadFinca = costoMovilidad(f, prog, d)
        val costoTotal = costoRiego + costoMovilidadFinca

        if (costoTotal < minCost)
          (costoTotal, prog)
        else
          (minCost, optProg)
    }

    (programacionOptima, costoMinimo)
  }



}