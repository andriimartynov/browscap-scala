Download [file](http://browscap.org/stream?q=BrowsCapCSV) to files folder.


Set project:

```sbtshell
project benchmarks
```

Run benchmark:

```sbtshell
jmh:run -prof jmh.extras.JFR -t1 -f 1 -wi 10 -i 20 .*DetectUserAgentsBenchmark.*
```