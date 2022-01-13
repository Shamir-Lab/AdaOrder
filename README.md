# AdaOrder

This implementation of AdaOrder corresponds to the following paper: .

To cite this paper use the following form: 


In `orders_minimizer_7.zip` are pre computed orders by AdaOrder, on 4 datasets used in the paper with `k=28,55`.

## Usage

First, compile the source code using your compiler to "AdaOrder.jar".

AdaOrder generates 2 files in the working directory: freq.txt, ranks.txt.
ranks.txt is the file with the generated minimizer order [Algorithm 1 in our paper]. 
freq.txt is the file with the estimated sizes if minimizers bins [Algorithm 2 in our paper]. 
In both files: line `i` corresponds to the minimizer with value `i` using `Natural`, and its value is its (rank \ bin size) among all m-mers.

        java -classpath "AdaOrder.jar" dumbo.OrderingOptimizer  -in <input-file>  <parameters>;      

AdaOrder can be controlled by several command line options and flags.

| Option               | Description   | Default |
|:---------------------|:--------------| -------:|
| `‑k <int>`   | Set the length of k-mers.  | `60` |
| `‑m <int>`          | Set the length m of minimizers.      |   `7` |
| `‑R <int>`    | Set the number of AdaOrder runs.      |    `1000` |
| `‑N <int>`          | Set the number of samples per round of AdaOrder.      |    `100000` |
| `‑p <float>`          | Set the penalty factor of AdaOrder.      |    `0.01` |
| `‑in <path>`                   | Dataset to generate AdaOrder for.      |    |

## DGerbil 
DGerbil is a modification of Gerbil, a memory efficient k-mer counter, that uses AdaOrder instaed of signature, for improved memory usage.
Its source code is found at https://github.com/Shamir-Lab/DGerbil.

## FGerbil
In the paper we present FGerbil - a modification of Gerbil that uses the frequency order instead of signature.
FGerbil is a variant that we generated for comparison only, and in fact was interior. The only difference between FGerbil and DGerbil is the order and minimizer partition statistics provided to the modified Gerbil code. 
Since it had inferior results we did not upload it to github as a tool.
