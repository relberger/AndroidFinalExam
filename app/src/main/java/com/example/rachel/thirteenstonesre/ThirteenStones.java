package com.example.rachel.thirteenstonesre;

import com.google.gson.Gson;

/**
 * Android Final Exam game
 * Fall '18
 */

public class ThirteenStones
{
    private final static int mDEFAULT_PILE_START = 13;

    private final static int MIN_PICK = 1;
    private final static int MAX_PICK = 3;

    private int mPileStart, mPileCurrent;
    private boolean mGameOver, mFirstPlayerTurn;

    public ThirteenStones ()
    {
        this(mDEFAULT_PILE_START);
    }

    public ThirteenStones (int pileSize)
    {
        mPileStart = pileSize;
        startGame();
    }

    public void startGame ()
    {
        mGameOver = false;
        mPileCurrent = mPileStart;
        mFirstPlayerTurn = true;
    }

    public void takeTurn(int amount)
    {
        if (!mGameOver)
        {
            tryToTakeTurnWith (amount);
        }
        else
        {
            throw new IllegalStateException ("May not take a turn while the game is over.");
        }
    }

    private void tryToTakeTurnWith (int amount)
    {
        if (isInMinToMaxRange (amount) && isNotGreaterThanPileCurrent (amount))
        {
            takeValidTurn (amount);
        }
        else {
            throw new IllegalArgumentException
                    ("Pick Amount must be: " + MIN_PICK + " - " + MAX_PICK +
                             " and up to number of remaining stones in the pile.");
        }
    }

    private boolean isNotGreaterThanPileCurrent (int amount)
    {
        return amount <= mPileCurrent;
    }

    private boolean isInMinToMaxRange (int amount)
    {
        return amount >= MIN_PICK && amount <= MAX_PICK;
    }

    private void takeValidTurn (int amount)
    {
        // decrement pile
        mPileCurrent-= amount;

        // end game if over
        mGameOver = mPileCurrent <= 0;

        // switch player turns even if Game Over: winning player is the next player after empty pile
        mFirstPlayerTurn = !mFirstPlayerTurn;
    }

    public boolean isGameOver ()
    {
        return mGameOver;
    }

    public int getPileCurrent ()
    {
        return mPileCurrent;
    }

    public int getCurrentOrWinningPlayerNumberOneOrTwo ()
    {
        return mFirstPlayerTurn ? 1 : 2;
    }

    public String getStatusBarText ()
    {
        return "Current Player: " + getCurrentOrWinningPlayerNumberOneOrTwo () +
                "; Stones remaining in pile: " + getPileCurrent ();
    }

    public String getRules()
    {
        return "13 Stones is a simple game. Game play begins with a pile of " +
                mPileStart + " stones.\n\n" +
                "Players each take one turn per round removing " +
                MIN_PICK + "-" + MAX_PICK + " stones per turn.\n\n" +
                "The player that empties the pile loses the game.";
    }

    public static int getMinPick ()
    {
        return MIN_PICK;
    }

    public static int getMaxPick ()
    {
        return MAX_PICK;
    }

    /**
     * Serializes the current game object
     * so it can be stored in the Bundle during rotation
     *
     * @param obj the game
     * @return Serialized (String) of the current game object
     */
    public static String getJSONof (ThirteenStones obj)
    {
        Gson gson = new Gson ();
        return gson.toJson (obj);
    }

    /**
     * Reverses the serialization of the game object String
     * back to a ThirteenStones game object
     *
     * @param json The serialized String of the game object
     * @return The game object
     */
    public static ThirteenStones getGameFromJSON (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, ThirteenStones.class);
    }
}

