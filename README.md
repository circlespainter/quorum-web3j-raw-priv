# Private raw transaction demo with [web3j/quorum](https://github.com/web3j/quorum)

## Intro

This small Kotlin application demonstrates how to use [web3j/quorum](https://github.com/web3j/quorum) to send private raw
transactions to a Quorum node.

It uses a simple `Counter.sol` Solidity smart contract for demo purposes and performs an `inc()` operation privately to
two nodes (a "from" node and a "to" node).

### Running

[Setup the 7nodes examples locally](https://github.com/jpmorganchase/quorum-examples#setting-up-locally): build the
latest release tag, use Raft and remember to run with Tessera as Constellation is not supported locally. If you want
you can reduce the number of nodes to at least 3.

[Proceed with the private transaction demo](https://github.com/jpmorganchase/quorum-examples/blob/master/examples/7nodes/README.md)
up to the smart contract deployment but use [`private-Counter.js`](./contract/private-Counter.js) instead of
`private-contract.js`.
Check that the contract has been deployed correctly and note the contract address via the transaction receipt.

You'll also need to note the public key of the "from" (first node) and "to" nodes that you can obtain e.g. with
`cat qdata/c1/tm.pub` from the 7nodes example directory after starting them.

Replace the values in [`Consts.kt`](./src/main/kotlin/com/dtcs/demo/dlt/quorumweb3j/rawpriv/Consts.kt) as follows:

- `CONTRACT_ADDR` should be the contract's ethereum address obtained from the receipt of the private deployment
transaction performed by 
- `PRIVATE_FOR_FROM_NODE_PUBK` should be the public key of the "from" node
- `PRIVATE_FOR_TO_NODE_PUBK` should be the public key of the "to" node

You shouldn't need to change anything else if you're using the 7nodes setup and the "from" node as first node.

Finally run `./gradlew run` and check the `getCount()` value in various nodes as explained shown in the 7nodes demo. You
can run it multiple times to increase the counter.

### Notes

- Tessera >= 0.8 is required with the [Third Party API](https://github.com/jpmorganchase/tessera/wiki/Interface-&-API#third-party---public-api)
enabled in the configuration (it is by default in 7nodes but not yet in [quorum-maker 2.6.2](https://github.com/synechron-finlabs/quorum-maker/tree/V2.6.2)
that uses Tessera 0.8 and the 0.7 conf. format) as the `web3j/quorum` library [invokes the `storaraw` API](https://github.com/web3j/quorum/blob/v4.0.6/src/main/kotlin/org/web3j/quorum/tx/QuorumTransactionManager.kt#L52).
- The contract is compiled with `solcjs --bin --abi Counter.sol` using `solc@0.5.0` and both the ABI and bytecode are provided;
later version might or might not produce bytecode that is valid for your 7nodes/Quorum version. The 7nodes Quorum node log usually
provides insight on why a transaction was unsuccessful (e.g. gas or invalid bytecode).