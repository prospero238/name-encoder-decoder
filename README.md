Assignment
---------------------------------------------------

Your utility class should contain a cache of previously encrypted names so that if you encrypt or decrypt a name, the cache is checked first to see if the name is there. You can implement the cache any way you would like.

Encryption algorithm:

 * Split the name into two equal parts. if it is an odd number length, then add a # at the end of the name to make the length even.
        Example:  Amin -> “Am”   “in”
        Example:  Vimal -> “Vim”  “al#” 
 * Reverse the parts and concatenate back into one string
        Example: Amin -> Am in -> inAm
        Example: Vimal -> Vim al# -> al#Vim   
 * Decryption would reverse this algorithm and remove the ‘#’ symbol if present.

Encryption method:

 * If the non-encrypted name is in the cache, just return the encrypted name
 * Otherwise encrypt the name per the encryption algorithm, add to the cache, and return the encrypted name

Decryption method:
    If the encrypted name is in the cache, return the non-encrypted name
    Otherwise decrypt the name by reversing the encryption algorithm, add to the cache, and return the non-encrypted name

My Effort Intro
---------------------------------------------------

The decode/encode routines reside in two different classes, SplitAndMixReassemblerDecoder and SplitAndMixEncoder respectively (links: [SplitAndMixReassemblerDecoder](https://github.com/prospero238/name-encoder-decoder/blob/master/src/main/java/com/throwawaycode/service/SplitAndMixReassemblerDecoder.java),[SplitAndMixEncoder](https://github.com/prospero238/name-encoder-decoder/blob/master/src/main/java/com/throwawaycode/service/SplitAndMixEncoder.java) 

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
willing to dispense with libraries if you'd prefer (though this would be contrary to how I'd normally develop!).
