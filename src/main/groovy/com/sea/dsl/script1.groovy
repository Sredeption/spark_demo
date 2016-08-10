package com.sea.dsl

def id = row.getLeftColumns['id']
def value = row.getLeftColumns['id']


if (id > table[value]) {
    row.explain 'data reference !!!'
}