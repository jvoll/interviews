// vending machine
// giving back change

    

// = [25, 10, 5, 1];
// amount is in cents
// coinDenominations sorted from largest to smallest
public HashMap<Integer, Integer> getChange(int amount, int [] coinDenominations, HashMap<Integer, Integer> availableCoins ) {

    HashMap<Integer, Integer> coinsToDispense = new HashMap<>();
    boolean coinAdded = true;
    
    while (amount > 0 && coinAdded) {
        coinAdded = false;
        
        for (int i = 0; i < coinsDenominations.length; i++) {
            if (coinsDenominations[i] <= amount && availableCoins.containsKey(coinDenominations[i]) && availableCoins.get(coinDenominations[i]) > 0) {
                if (coinsToDispense.containsKeys(coinDenominations[i])) {
                    int cur = coinsToDispense.get(coinDenominations[i]);
                    coinsToDispense.put(coinDenominations[i], cur + 1);                
                }
                else {
                    coinsToDispense.put(coinDenomnations[i], 1);
                }
                availableCoins.put(coinDenominations[i], availableCoins.get(coinDenominations[i]) - 1);
                amount -= coinDenominations[i];
                coinAdded = true;
                break;
            }
        }
    }
    
    if (amount > 0) {
        return coinsToDispense.empty();
    }
    
    
    for (int i = 0; i < coinDenominations.length; i++) {
        coin
    }
    
    return coinsToDispense;

}
