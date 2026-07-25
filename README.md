# The Cypher Guide

A practical guide to understanding encryption, from ancient ciphers
to modern bank-grade algorithms.

---

## TABLE OF CONTENTS

1. [The 6 Ciphers in This Tool](#the-6-ciphers)
2. [How to Run the Java Code](#how-to-run)
3. [Beyond the Basics: More Classical Ciphers](#more-classical)
4. [Bank-Grade Encryption: The Heavy Hitters](#bank-grade)
5. [One-Time Pad (OTP): The Unbreakable Cipher](#otp)
6. [Asymmetric Encryption: The Key Pair Revolution](#asymmetric)
7. [Layered Encryption: Belt and Suspenders](#layered)

---

## THE 6 CIPHERS

These are the ciphers implemented in our CypherTool.java. Each one
teaches a different fundamental concept in cryptography.

---

### 1. ROT13

**Concept:** Simple substitution via letter shifting.

```
PLAIN:    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
CIPHER:   N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
```

**How to encrypt:** Move each letter 13 positions forward in the alphabet.

```
Example:
  H E L L O
  +13 +13 +13 +13 +13
  U R Y Y B
```

**Fun fact:** ROT13 is its own decryption method. Apply it twice
and you get back to the original text. This works because the
alphabet has 26 letters, and 13 is exactly half of 26.

**Real-world use:** Used on forums and websites to hide spoilers
or puzzle solutions. Not secure at all, but great for casual
obfuscation.

**Vulnerability:** There are only 26 possible shifts. A brute
force attack takes about 2 seconds with a computer.

---

### 2. ATBASH

**Concept:** The alphabet mirror.

```
PLAIN:    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
CIPHER:   Z Y X W V U T S R Q P O N M L K J I H G F E D C B A
```

**How to encrypt:** Replace each letter with its reverse.

```
Example:
  H E L L O
  S V O O L
```

**Fun fact:** Atbash is mentioned in the Bible! In Jeremiah 25:26,
the Hebrew word "Sheshach" is believed to be "Babylon" encoded
using Atbash. That makes it one of the oldest known ciphers,
dating back over 2,600 years.

**Real-world use:** Educational purposes and simple text puzzles.

**Vulnerability:** Like ROT13, it only has one possible key.
Frequency analysis cracks it instantly.

---

### 3. CAESAR CIPHER

**Concept:** Shift each letter by a fixed number of positions.

```
PLAIN:    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
CIPHER:   D E F G H I J K L M N O P Q R S T U V W X Y Z A B C
(shift = 3)
```

**How to encrypt:** Choose a shift number, then move each letter
that many positions forward.

```
Example (shift = 5):
  W A R
  +5 +5 +5
  B F W
```

**Fun fact:** Julius Caesar used a shift of 3 to protect his
military messages. His generals could decode them, but his
enemies could not. Well, until frequency analysis was invented.

**Real-world use:** Historical interest. The method was used
by various military leaders for centuries.

**Vulnerability:** There are only 25 possible shifts. Also
vulnerable to frequency analysis (E is the most common letter
in English, so look for the most common letter in the ciphertext).

---

### 4. RAIL FENCE

**Concept:** Transposition cipher using a zigzag pattern.

```
Message: "WEAREDISCOVEREDFLEEATONCE"
Rails: 3

Zigzag pattern:
W . . . E . . . C . . . R . . . E . . . E . . . A . . . N
. E . A . E . D . S . O . V . E . E . F . L . E . T . O . C
. . R . . . I . . . V . . . D . . . L . . . T . . . E . . .

Read row by row:
Row 0: W E C R E E A N
Row 1: E A E D S O E F L E T O C
Row 2: R I V D L T E

Encrypted: WECREEAN AEEDSOE FLET O C RIVDLTE
```

**How it works:** Write the message diagonally on a fence with
the specified number of rails, then read it horizontally.

**Fun fact:** This is a TRANSPOSITION cipher, not a substitution
cipher. The letters don't change, only their positions do.
It's like shuffling a deck of cards while keeping each card
face up.

**Real-world use:** Used in some WWII military communications
as part of more complex encryption systems.

**Vulnerability:** Easy to crack with pencil and paper. The key
space is just the number of rails (usually 2-10).

---

### 5. VIGENERE CIPHER

**Concept:** Polyalphabetic substitution using a keyword.

```
Message: SECRET
Keyword: KEYKEY (repeated to match length)

S E C R E T
K E Y K E Y
--------
Each letter shifts by the keyword letter's position:
S(18) + K(10) = C(2)
E(4) + E(4)  = I(8)
C(2) + Y(24) = U(0)
R(17) + K(10) = B(1)
E(4) + E(4)  = I(8)
T(19) + Y(24) = N(13)

Result: "CIUBIN"
```

**How it works:** Each letter of the plaintext is shifted by
the corresponding letter of the keyword. The keyword repeats
to cover the entire message.

**Fun fact:** When it was invented in 1553 by Giovan Battista
Bellaso, it was called "le chiffre indechiffrable" - the
indecipherable cipher. It resisted systematic decryption for
nearly 300 years until Charles Babbage broke it in 1854.

**Real-world use:** Used in the Enigma machine (a more complex
version), and in the WWII SIGABA cipher.

**Vulnerability:** Once the keyword length is known (via
Kasiski examination), it can be broken into multiple Caesar
ciphers.

---

### 6. BASE64

**Concept:** Binary-to-text encoding.

```
Plain text: "Man"

Binary:  01001101 01100001 01101110
Split:   010011 010110 000101 101110
Index:   19     22     5      46
Base64:  T      W      F      u

Result: "TWFu"
```

**How it works:** Every 3 bytes (24 bits) of input become 4
Base64 characters (6 bits each). The Base64 alphabet uses
A-Z, a-z, 0-9, +, and /.

**Fun fact:** Base64 is NOT encryption! It is ENCODING. Anyone
can decode it. It's like translating English to French - it
changes the representation but anyone who knows French can
read it. However, it IS used as part of encryption systems
to safely transmit binary data over text-based protocols.

**Real-world use:**
- Email attachments (MIME standard)
- Embedding images in HTML/CSS
- Storing data in JSON
- HTTP Basic Authentication headers
- Bitcoin addresses

**Vulnerability:** None to break, because there's nothing to
break. It's encoding, not encryption. Always use it with
actual encryption, never alone.

---

## HOW TO RUN

### Prerequisites

You need the Java Development Kit (JDK) installed. Check with:

```bash
java -version
javac -version
```

If not installed:
- Ubuntu/Debian: `sudo apt install default-jdk`
- macOS: `brew install openjdk`
- Windows: Download from oracle.com or use `winget install Microsoft.OpenJDK.21`

### Running the Tool

**Option 1: Compile and run (recommended for learning)**

```bash
# Step 1: Compile both Java files
javac Main.java CypherTool.java

# Step 2: Run the Main class
java Main
```

**Option 2: Run directly with modern Java (no compile step)**

```bash
# Java 11+ can run single-file source-code programs
java CypherTool.java
```

### What You'll See

```
============================================
         Welcome to the Cypher Tool!
============================================

  6 ciphers to explore:
    1. ROT13      - The classic 13-shift
    2. Atbash     - The alphabet mirror
    3. Caesar     - Julius Caesar's favorite
    4. Rail Fence - The zigzag transposition
    5. Vigenere   - The polyalphabetic puzzle
    6. Base64     - The internet's workhorse

  Type 'exit' at any time to quit.
============================================

Select operation:
  1. Encrypt
  2. Decrypt
> 1

Select cipher:
  1. ROT13
  2. Atbash
  3. Caesar
  4. Rail Fence
  5. Vigenere
  6. Base64
> 1

Enter the message:
> Hello, World!

============================================
Encrypted message (ROT13):
============================================
Uryyb, Jbeyq!
============================================
```

### Tips

- You can type 'exit' at any prompt to quit the program
- Non-alphabetic characters (spaces, numbers, punctuation)
  are always preserved unchanged
- For Caesar cipher, try different shift values and see
  how the output changes
- For Vigenere, experiment with different keywords

---

## MORE CLASSICAL CIPHERS

Beyond the 6 in our tool, here are more ciphers worth knowing.

---

### SUBSTITUTION CIPHER

**Concept:** Replace each letter with a different letter.

```
PLAIN:    A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
CIPHER:   Q W E R T Y U I O P A S D F G H J K L Z X C V B N M
(You make up the mapping)
```

**How it works:** You create a custom alphabet mapping. Unlike
Caesar where the shift is consistent, you can arrange the
cipher alphabet however you want.

**Fun fact:** Edgar Allan Poe was fascinated by substitution
ciphers. He wrote "The Gold-Bug" (1843), a short story that
features a substitution cipher as a key plot element. The story
helped popularize cryptography in America.

**Vulnerability:** Frequency analysis. The letter 'E' appears
most often in English (~13%), so look for the most common
letter in the ciphertext.

---

### BACON'S CIPHER

**Concept:** Hidden messages using two fonts.

```
A = aaaaA  B = aaaAa  C = aaAaa  D = aaAAA  E = aAaaa
F = aAaAa  G = aAAAA  H = AAAaa  I = aAAaa  J = aAAAa
K = Aaaaa  L = AaAaa  M = AaAAa  N = AaAaa  O = Aaaaa (overloaded)
...

Message: "HI"
Binary:  00111 00110
Code:    aaaAA aaaAa
```

**How it works:** Each letter is represented by a 5-character
sequence of 'a' and 'A' (or two different fonts). The message
is hidden in plain sight within seemingly normal text.

**Fun fact:** Francis Bacon invented this in 1605. You could
write a love letter where every other word was in slightly
different font, and only someone who knew the cipher could
read the hidden message.

**Real-world use:** Steganography - hiding messages within
other messages. Used in some spy tradecraft.

---

### MORSE CODE

**Concept:** Represent letters as dots and dashes.

```
A: .-      B: -...    C: -.-.    D: -..     E: .
F: ..-.    G: --.     H: ....    I: ..      J: .---
K: -.-     L: .-..    M: --      N: -.      O: ---
P: .--.    Q: --.-    R: .-.     S: ...     T: -
U: ..-     V: ...-    W: .--     X: -..-    Y: -.--
Z: --..
```

**Example:**
```
S O S
... --- ...
```

**Fun fact:** Morse code was invented in 1836 for telegraph
communication. SOS (... --- ...) became the universal distress
signal in 1906. It was chosen because it's simple to remember
and unmistakable, even in bad signal conditions.

**Real-world use:** Still used in aviation and maritime
communication. Some amateur radio operators use it daily.

---

### PLAYFAIR CIPHER

**Concept:** Encrypt pairs of letters using a 5x5 grid.

```
Key: "MONARCHY"
Grid:
  M O N A R
  C H Y B D
  E F G I K
  L P Q S T
  U V W X Z

Rules:
- Break message into letter pairs (HI -> HI, EL -> EL)
- If a pair has the same letter, insert 'X' (LL -> LX)
- Apply rules based on grid position
```

**Example:**
```
Plaintext:  HI
Grid positions: H is at (1,1), I is at (2,3)

Rectangle rule: Take opposite corners
  H -> Y
  I -> G

Ciphertext: YG
```

**Fun fact:** Invented by Charles Wheatstone in 1854, but named
after Lord Playfair who promoted it. It was the first practical
digraph substitution cipher and was used in World War I.

---

### HILL CIPHER

**Concept:** Use matrix multiplication to encrypt.

```
Message: "ACT"
Convert to numbers: A=0, C=2, T=19

Matrix key: [[3, 3], [2, 5]]

Calculation:
[3 3] [0]   [3*0 + 3*2]   [6]  = G
[2 5] [2] = [2*0 + 5*2] = [10] = K
         [19]

Ciphertext: "GK..."
```

**How it works:** Convert letters to numbers, arrange them in
vectors, and multiply by a key matrix. Decryption uses the
inverse matrix.

**Fun fact:** The Hill cipher was invented by mathematician
Lester Hill in 1929. It was the first cipher to use linear
algebra. It's practically unbreakable by hand for large matrices.

**Vulnerability:** Known-plaintext attack. If you know a few
letters of the plaintext and their ciphertext equivalents,
you can solve for the key matrix.

---

## BANK-GRADE ENCRYPTION

These are the algorithms that protect your money, your data,
and your privacy in the modern world.

---

### AES (Advanced Encryption Standard)

**What it is:** The current gold standard for symmetric encryption.

**Key sizes:** 128, 192, or 256 bits

**How it works:**
```
Plain text
    |
    v
[SubBytes] -> [ShiftRows] -> [MixColumns] -> [AddRoundKey]
    |            |              |               |
    +---- Round 1 to 10 (128-bit) or 14 (256-bit) ----+
    |
    v
Cipher text
```

Each round performs four operations:
1. SubBytes: Replace each byte using a lookup table
2. ShiftRows: Shift each row of the state matrix
3. MixColumns: Mix the columns using polynomial math
4. AddRoundKey: XOR with a subkey derived from the master key

**Fun fact:** AES was selected by NIST in 2001 after a 5-year
competition. The winner was an algorithm called Rijndael,
created by two Belgian cryptographers. It beat 14 other
candidates. AES is now used by banks, governments, and
basically everyone on the internet.

**Key size perspective:**
```
AES-128: 2^128 possible keys
         = 340,282,366,920,938,463,463,374,607,431,768,211,456
         = More than the number of atoms in the observable universe
         = A supercomputer trying a billion keys per second
           would take longer than the age of the universe
```

---

### 3DES (Triple DES)

**What it is:** DES applied three times for extra security.

```
Plain text -> [DES Encrypt] -> [DES Decrypt] -> [DES Encrypt] -> Cipher text
              with Key 1       with Key 2       with Key 3
```

**How it works:** Encrypt, decrypt, encrypt with three different
keys. The middle decrypt step seems weird but it provides
backward compatibility with single DES if you use the same
key for all three.

**Fun fact:** 3DES was a "quick fix" when single DES was
cracked in 1999. It's like locking your door three times
with three different locks. Effective, but awkward. It's being
phased out in favor of AES.

**Status:** Deprecated. Being replaced by AES everywhere.

---

### RSA (Rivest-Shamir-Adleman)

**What it is:** The most widely used asymmetric encryption algorithm.

**Key concept:** Based on the difficulty of factoring large numbers.

```
How RSA works (simplified):

1. Pick two large prime numbers: p and q
2. Compute n = p * q
3. Compute e (public exponent)
4. Compute d (private exponent) using modular math

Public key:  (e, n)  - share this with everyone
Private key: (d, n)  - keep this secret

Encrypt:  ciphertext = message^e mod n
Decrypt:  message = ciphertext^d mod n
```

**Fun fact:** RSA was invented in 1977 and is named after its
three inventors: Ron Rivest, Adi Shamir, and Leonard Adleman.
It was one of the first practical public-key cryptosystems.
The RSA algorithm protects every HTTPS connection, every
digital signature, and most encrypted email.

**Key size perspective:**
```
RSA-2048: Used today for banking
RSA-4096: Maximum security
RSA-2048 is estimated to be secure until at least 2030
```

---

### ECC (Elliptic Curve Cryptography)

**What it is:** Asymmetric encryption based on elliptic curves
instead of factoring.

```
How it works (simplified):

An elliptic curve: y^2 = x^3 + ax + b

    y
    |
    |     *
    |   *   *
    |  *     *
    | *       *
    |*         *
----+------------ x
    |*         *
    | *       *
    |  *     *
    |   *   *
    |     *
```

Points on the curve can be "added" together using special
math. Given two points P and Q, you can compute P + Q.
But given P and P + Q, finding Q is computationally infeasible.

**Fun fact:** ECC provides the same security as RSA but with
much smaller key sizes. A 256-bit ECC key equals roughly
a 3072-bit RSA key. This makes it perfect for mobile devices
where processing power and battery life matter.

**Key size comparison:**
```
Security level: 128-bit
RSA:    3072-bit key
ECC:     256-bit key
ECC wins by 12x in key size!
```

---

### BLOWFISH / TWOFISH

**What they are:** Fast symmetric block ciphers designed by
Bruce Schneier.

```
Blowfish:
- Block size: 64 bits
- Key size: 32-448 bits
- Rounds: 16
- Status: Deprecated (use Twofish instead)

Twofish:
- Block size: 128 bits
- Key size: 128, 192, or 256 bits
- Rounds: 16
- Status: Still secure, was an AES finalist
```

**Fun fact:** Blowfish was designed by Bruce Schneier in 1993.
It was one of the first open-source encryption algorithms
and was free to use. Twofish was his follow-up submission to
the AES competition. Schneier is famous for saying "Attacks
always get better; they never get worse."

---

## OTP: ONE-TIME PAD

**Concept:** The only theoretically unbreakable encryption method.

```
How it works:

Plaintext:   H E L L O    (72 69 76 76 79)
Key:         X Q Z M K    (88 81 90 77 75)
             -------------
XOR result:  W C K F N    (16 20 125 21 6)
Ciphertext:  WCKFN
```

**The rules (all must be true):**
1. The key must be truly random
2. The key must be at least as long as the message
3. The key must NEVER be reused
4. The key must be kept completely secret

**The math:**
```
Encrypt: plaintext XOR key = ciphertext
Decrypt: ciphertext XOR key = plaintext
```

XOR truth table:
```
0 XOR 0 = 0
0 XOR 1 = 1
1 XOR 0 = 1
1 XOR 1 = 0
```

**Why it's unbreakable:** If the key is truly random and
never reused, every possible plaintext is equally likely.
The ciphertext gives the attacker zero information about
the original message.

```
Message:    HELLO
Key 1:      XQZMK  ->  ciphertext: WCKFN
Key 2:      ABCDE  ->  ciphertext: PJIDM
Key 3:      ZZZZZ  ->  ciphertext: YKKKY

All three ciphertexts are equally valid!
```

**Fun fact:** The One-Time Pad was used by Soviet spies during
the Cold War. The US government has a VENONA project dedicated
to trying to break Soviet OTP-encrypted messages. Because the
Soviets sometimes REUSED keys, the US was able to decrypt
some messages. This is why rule #2 (never reuse) is critical.

**The problem:** Key distribution. If you need to send a
1GB encrypted file, you first need to securely deliver a 1GB
key to the recipient. This is impractical for most uses,
which is why we use RSA/ECC to exchange AES keys instead.

**Real-world use:**
- The hot line between Washington and Moscow was reportedly
  secured with OTP during the Cold War
- Quantum Key Distribution (QKD) systems aim to solve the
  key distribution problem for OTP

---

## ASYMMETRIC ENCRYPTION

Also called "public-key cryptography." The revolution that
made modern internet security possible.

---

### THE PROBLEM

With symmetric encryption (like AES), both parties need the
same key. How do you agree on a key without an eavesdropper
hearing it?

```
Alice ----[?]----> Bob
           |
     Eve intercepts everything
     If Alice sends the key,
     Eve gets it too!
```

### THE SOLUTION

Use TWO keys that are mathematically related:
- Public key: Can be shared with anyone
- Private key: Must be kept secret

```
Alice                              Bob
  |                                  |
  |-- "Hey Bob, here's my public" -->|
  |   (Eve can see this, doesn't matter)
  |                                  |
  |<-- "Here's my public key too" ---|
  |                                  |
  |  Alice encrypts with Bob's      |
  |  public key                      |
  |-- [encrypted message] ---------> |
  |   (Eve sees this but can't      |
  |    decrypt without Bob's        |
  |    private key)                  |
  |                                  |
  |                          Bob decrypts with
  |                          his private key
```

### HOW RSA WORKS (Simplified)

```
1. Bob picks two prime numbers: p=61, q=53
2. Computes n = 61 * 53 = 3233
3. Computes e = 17 (public exponent)
4. Computes d = 2753 (private exponent)
   (Using extended Euclidean algorithm)

Public key:  (17, 3233)
Private key: (2753, 3233)

Encrypt message "65":
  65^17 mod 3233 = 2790

Decrypt ciphertext "2790":
  2790^2753 mod 3233 = 65
```

### DIGITAL SIGNATURES

The reverse process proves identity:

```
Signing:
  Alice signs with HER PRIVATE key
  Anyone can verify with Alice's PUBLIC key

  signature = hash(message)^private_key mod n

Verification:
  is_valid = (signature)^public_key mod n == hash(message)?
```

**Fun fact:** When you visit a website with HTTPS, your browser
uses asymmetric encryption to agree on a symmetric key with
the server. This is called the TLS handshake. Then both sides
switch to fast symmetric encryption (usually AES) for the
actual data. It's like using asymmetric encryption as an
intro, then symmetric encryption for the ongoing conversation.

---

## LAYERED ENCRYPTION

Real security uses multiple ciphers together. This is called
"defense in depth" or "layered encryption."

---

### THE ONION MODEL

Think of it like an onion - multiple layers, each protecting
the one inside.

```
Layer 3: AES-256 (fast bulk encryption)
   |
Layer 2: RSA-2048 (key exchange)
   |
Layer 1: HMAC (integrity verification)
   |
Plaintext: "Your credit card number"
```

### HOW TLS/SSL WORKS (What HTTPS Uses)

```
Step 1: HANDSHAKE (Asymmetric)
  Browser <---> Server
  |  "Hello, I support TLS 1.3"
  |  "Here's my certificate"
  |  "Let's agree on a key"
  |  (Using RSA or ECDHE)
  |
Step 2: KEY EXCHANGE
  |  Browser generates a random session key
  |  Encrypts it with server's public key
  |  Server decrypts with private key
  |  Both now have the same session key
  |
Step 3: ENCRYPTED COMMUNICATION (Symmetric)
  |  All data encrypted with AES-256-GCM
  |  Fast, efficient, secure
  |
Step 4: INTEGRITY CHECK
  |  Each message includes HMAC
  |  Detects any tampering
```

### PGP/GPG ENCRYPTION

Pretty Good Privacy (used for email encryption):

```
Step 1: Compress the message (optional, saves space)
Step 2: Generate random session key
Step 3: Encrypt message with AES (using session key)
Step 4: Encrypt session key with recipient's public RSA key
Step 5: Combine both parts

Result:
  [RSA-encrypted AES key] + [AES-encrypted message]
```

**Fun fact:** This is exactly how most encryption systems work
in practice. You use asymmetric encryption to securely exchange
a symmetric key, then use the symmetric key for the actual data.
It's the best of both worlds - asymmetric for key exchange,
symmetric for speed.

---

### ENCRYPTING AT REST vs IN TRANSIT

**In transit** (moving data):
- TLS/SSL for web traffic
- SSH for terminal connections
- Signal Protocol for messaging

**At rest** (stored data):
- Full disk encryption (BitLocker, FileVault, LUKS)
- Database encryption (AES-256)
- File-level encryption (GPG, age)

```
Data lifecycle:

  Created -> Encrypted at rest -> Encrypted in transit
     |            |                     |
     v            v                     v
  [App]    [Encrypted disk]    [TLS tunnel]
                                     |
                              Encrypted in transit
                                     |
                                     v
                              [Server disk]
                                     |
                              Encrypted at rest
                                     |
                                     v
                              [Read by recipient]
```

---

## QUICK REFERENCE

```
Cipher          | Type      | Speed | Security | Best For
----------------|-----------|-------|----------|------------------
ROT13           | Substitute| Fast  | None     | Fun, education
Atbash          | Substitute| Fast  | None     | Fun, education
Caesar          | Substitute| Fast  | None     | Fun, education
Rail Fence      | Transpose | Fast  | Weak     | Fun, education
Vigenere        | Polyalpha | Fast  | Moderate | Historical
Base64          | Encoding  | Fast  | None     | Data transport
AES             | Symmetric | Very  | Excellent| Everything modern
RSA             | Asymmetric| Slow  | Good     | Key exchange
ECC             | Asymmetric| Med   | Excellent| Mobile, modern
OTP             | Unknown   | Fast  | Perfect  | Diplomacy
```

---

## FURTHER READING

Books:
- "The Code Book" by Simon Singh
- "Applied Cryptography" by Bruce Schneier
- "Serious Cryptography" by Jean-Philippe Aumasson

Online:
- Cryptopals Challenges (cryptopals.com)
- Crypto101 (crypto101.io)
- NIST Computer Security Resource Center

Remember: The best encryption is the one you understand and use
correctly. A simple cipher applied properly beats a complex
cipher applied poorly.

---

"The enemy knows the system." - Kerckhoffs's Principle, 1883

The security of a cipher should depend only on the secrecy of
the key, not on the secrecy of the algorithm.
