package com.sea.demo

/**
  * Created by Sea on 7/16/2016.
  */
trait Similarity {
  def isSimilar(x:Any): Boolean
  def isNotSimilar(x:Any): Boolean= !isSimilar(x)
}
