**Jaccard Similarity of Sets**
The **Jaccard similarity** of sets S and T is ```|S ∩ T|/|S ∪ T|```, that is,
the ratio of the size of the intersection of S and T to the size of their union.
Denote the Jaccard similarity of S and T by ```SIM(S, T )```.

## Shingling of Documents
**k-shingle**
A document is a string of characters. Define a k-shingle for a document to be
any substring of length k found within the document.Then, we may associate with
each document the set of k-shingles that appear one or more times within
that document.

**How to choose k**
How large k should be depends on how long typical documents are and how large
the set of typical characters is. The important thing to remember is:
    
    k should be picked large enough that the probability of any given shingle
appearing in any given document is low.

**Hashing Shingles**
Instead of using substrings directly as shingles, we can pick a hash function
that maps strings of length k to some number of buckets and treat the resulting
bucket number as the shingle.

**Notice that we can differentiate documents better if we use 9-shingles and
hash them down to four bytes than to use 4-shingles, even though the space used
to represent a shingle is the same.** The reason was touched upon in Section
3.2.2. If we use 4-shingles, most sequences of four bytes are unlikely or
impossible to find in typical documents. Thus, the effective number of different
shingles is much less than 2^32 − 1. If we assume only 20 characters are
frequent in English text, then the number of different 4-shingles that are
likely to occur is only (20)^4 = 160,000. However, if we use 9-shingles, there
are many more than 2^32 likely shingles. 

**Shingles built from words**
ignore stop words


> Written with [StackEdit](https://stackedit.io/).
