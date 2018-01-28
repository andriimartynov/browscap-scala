package test

import java.util.concurrent.TimeUnit

import net.github.andriimartynov.browscap.{BrowscapDataStore, BrowscapUserAgentParser}
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit}

import scala.io.Source

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class InitBenchmark {

  @Benchmark
  def init(): Unit = {
    val iterator = Source
      .fromFile("files/browscap.csv")
      .getLines()
    val dataStore = BrowscapDataStore(iterator)

    val parser = new BrowscapUserAgentParser(dataStore)
  }
}
