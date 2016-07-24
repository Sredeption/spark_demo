package com.sea.dsl

def d1 = new DramaDataSource()
d1.database = "d1"
d1.Schema = ""
d1.table = ""

def d2 = new DramaDataSource()

t = new DramaTables()
t.left = "select * from " + d2 + " where time= " + new Trigger("time")
t.right = "select * from " + d1;

def m = new MatchRules()
def r = new ReconRules()

def e = new Explainer("explainer1")

print(t.left)
