package A306.blockchain;



import java.math.BigInteger;


/**
 * The class chain: is used to adjust target difficulty and generate new target so that the miners can create
 * a block for the blockchain.
 *
 */
public class Chain {

    // Two weeks (in seconds);wanted time for 2016 blocks.
    private static final long targetTimespan = 14 * 24 * 60 * 60;

    /*BigInteger is used for large Integers, i.e greater than 64-bit
    * The BigInteger constructor: takes the string representation of a big int and the base(radix) to make a BigInteger
    * The proofOfWorkLimit : is maximum target, and is what difficulty 1 sets the hash block
    * target as. (the bits field  is compact difficulty)
    */
    private static final BigInteger proofOfWorkLimit = new BigInteger("00000000 FF FF 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00", 16); // Highest target (difficulty 1)
    private static Target chainTarget = new Target(proofOfWorkLimit);


    /*public Target getTarget(){
        return chainTarget;
    }
    */

    public BigInteger getProofOfWorkLimit(){
        return proofOfWorkLimit;
    }

    /**
     * The function adjustDifficulty: adjusts the difficulty based on 'if the previous 2016 blocks took more than two
     * weeks to find, the difficulty is reduced. If they took less than two weeks, the difficulty is increased. '
     */
    void adjustDifficulty() {

        Block lastBlock = new Block("", null);
        Block firstBlock = new Block("", null); // Find block that is lastBlock - 2016.


        System.out.println("pow limit: " + proofOfWorkLimit.toString(16));


        //Find the actual time it took for the 2016 blocks to be generated??
        long actualTimespan = lastBlock.getTimestamp() - firstBlock.getTimestamp();

        actualTimespan = targetTimespan / 2;

//        if (actualTimespan < targetTimespan / 4) {
//            actualTimespan = targetTimespan / 4;
//        } else if (actualTimespan > targetTimespan * 4) {
//            actualTimespan = targetTimespan * 4;
//        }


        //
        BigInteger newBigIntegerTarget = new Target(lastBlock.getCompactDifficulty()).getBigIntegerTarget();

        //Calculate new target
        newBigIntegerTarget = newBigIntegerTarget.multiply(BigInteger.valueOf(actualTimespan));
        newBigIntegerTarget = newBigIntegerTarget.divide(BigInteger.valueOf(targetTimespan));

        //New target for nodes; hash must be less than target
        Target newTarget = new Target(newBigIntegerTarget);
//        System.out.println("New target in hex: " + newTarget.getBigIntegerTarget().toString(16));
//        System.out.println("New target in compact form: " + newTarget.getCompactTarget());


    }
}
