package com.rajkarnikarunish.fetchrewards

class AlphaNumComparator: Comparator<String> {
    
    private fun isDigit(ch: Char) : Boolean {
        return ch in '0'..'9'
    }
    
    private final fun getChunk(s: String, marker: Int) : String {
        val slength = s.length
        val chunk = StringBuilder()
        var c: Char = s[marker]
        chunk.append(c)
        var newMarker = marker +1
        
        if(isDigit(c)) {
            while(newMarker<slength) {
                c = s[newMarker]
                if (!isDigit(c)) {
                    break
                }
                chunk.append(c)
                newMarker++
            }
        } else {
            while(newMarker < slength) {
                c = s[newMarker]
                if (isDigit(c))
                    break
                chunk.append(c)
                newMarker++
            }
        }
        return chunk.toString()
    }
    
    override fun compare(s1: String?, s2: String?): Int {
        if (s1==null || s2 == null) {
            return 0
        }
        
        var thisMarker = 0
        var thatMarker = 0
        val s1length = s1.length
        val s2length = s2.length
        
        while(thisMarker < s1length && thatMarker < s2length) {
            val thisChunk = getChunk(s1, thisMarker)
            thisMarker += thisChunk.length
            
            val thatChunk = getChunk(s2, thatMarker)
            thatMarker += thatChunk.length
            
            var result = 0
            if(isDigit(thisChunk[0]) && isDigit(thatChunk[0])) {
                val thisChunkLength = thisChunk.length
                result = thisChunkLength - thatChunk.length
                
                if (result == 0) {
                    for (i in 0 until thisChunkLength) {
                        result = thisChunk[i] - thatChunk[i]
                        if (result != 0) {
                            return result
                        }
                    }
                }
            } else {
                result = thisChunk.compareTo(thatChunk)
            }
            
            if(result != 0) {
                return result
            }
        }
        return s1length - s2length
    }
}