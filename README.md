Introduction
==============================

The decode/encode routines reside in two different classes, SplitAndMixReassemblerDecoder and SplitAndMixEncoder respectively (links: SplitAndMixReassemblerDecoder 

Unit tests exist for each class.  You'll also find a test that verifies cache usage.

There's also a command-line interface.  Invoke like so:
 
<pre>java -jar _path to jar_ (decode|encode) list of names... </pre>
For example:

<pre>
# decode
java -jar target/name-encoder-decoder-0.0.1-SNAPSHOT.jar decode inAm al#Vim

# encode
java -jar target/name-encoder-decoder-0.0.1-SNAPSHOT.jar decode Amin Vimal
</pre>

Decode/encode results are provided on stdout, as well as cache statistics. 


Caching
==============================

I took a liberal interpretation of "implement the cache any way you would like" and used ehcache here.  I am certainly 
willing to dispense with libraries if you'd prefer (though contrary to how I'd normally develop!).
 