def v1 = left('p2') + right('p5')
println(v1)
println(leftTable_p1.gt(23))

when(v1.eq(285))

def c1 = ds1.select(agg("c1")).where(ds1("c1") == v1)
