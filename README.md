# Private raw transaction demo with [web3j/quorum](https://github.com/web3j/quorum)

## Intro

These small Kotlin applications demonstrate how to use [web3j/quorum](https://github.com/web3j/quorum) to send private
raw transactions to a Quorum node, both with the AST API and with the Java contract wrappers
[generated using the web3j command line tools](https://docs.web3j.io/smart_contracts.html#smart-contract-wrappers).

They call a simple `Counter.sol` Solidity smart contract and perform an `inc()` operation privately between
two nodes (a "from" node and a "to" node); they also retrieve and print the updated counter value through `getCount()`. 

### Running

[Setup the 7nodes examples locally](https://github.com/jpmorganchase/quorum-examples#setting-up-locally): build the
latest release tag, use Raft and remember to run with Tessera as Constellation is not supported locally. If you want
you can reduce the number of nodes to e.g. 3 (preferred) or 2.

[Proceed with the private transaction demo](https://github.com/jpmorganchase/quorum-examples/blob/master/examples/7nodes/README.md)
up to the smart contract deployment but use [`private-Counter.js`](./contract/private-Counter.js) instead of
`private-contract.js`.
Check that the contract has been deployed correctly and note the contract address via the transaction receipt.

You'll also need to note the public key of the "from" (first node) and "to" nodes that you can obtain e.g. with
`cat qdata/c1/tm.pub` from the 7nodes example directory after starting them.

If you're using [`quorum-maker` 2.6.2](https://github.com/synechron-finlabs/quorum-maker/tree/V2.6.2) you can deploy the
contract and copy the contract address more comfortably through the UI.

Now replace the values in [`Consts.kt`](./src/main/kotlin/com/dtcs/demo/dlt/quorumweb3j/rawpriv/Consts.kt) as follows:

- `CONTRACT_ADDR` has to be set to the contract's Ethereum address obtained from the receipt of the deployment transaction.
- `PRIVATE_FOR_FROM_NODE_PUBK` has to be set to the public key of the "from" node.
- `PRIVATE_FOR_TO_NODE_PUBK` has to be set to the public key of the "to" node.
- `QUORUM_FROM_NODE_URL` has to be set to the JSON-RPC URL of the Quorum node you want to connect to.
- `TESSERA_URL` has to be set to 3rd-party API Tessera URL (see the notes below about the Tessera configuration) _without the port_.
- `TESSERA_PORT` has to be set to 3rd-party API Tessera URL's _port_ (see the notes below about the Tessera configuration).

You shouldn't need to change anything else if you're using the 7nodes setup and the "from" node as first node.

If you're using [`quorum-maker` 2.6.2](https://github.com/synechron-finlabs/quorum-maker/tree/V2.6.2) take the keys and
address information from the UI; on Linux the URLs will be similar to the following values (see the notes below about
the Tessera configuration):

```kotlin
internal const val QUORUM_FROM_NODE_URL = "http://10.50.0.2:22000"
internal const val TESSERA_URL = "http://10.50.0.2"
internal const val TESSERA_PORT = 22006
```

Finally run `./gradlew run` and check the `getCount()` value in the various nodes, similar to the 7nodes demo. You
can run multiple times to increase the counter further.

As an alternative you can also run `MainWithWrapper` that uses the
[`Counter.java`](./src/main/kotlin/com/dtcs/demo/dlt/quorumweb3j/rawpriv/Counter.java) contract wrapper
[generated using the web3j command line tools](https://docs.web3j.io/smart_contracts.html#smart-contract-wrappers) like so:

```bash
$ web3j solidity generate -a Counter.abi -o . -p mypackage
```

### Notes

- Tessera 0.8 is required with the [Third Party API](https://github.com/jpmorganchase/tessera/wiki/Interface-&-API#third-party---public-api)
enabled in the configuration as the `web3j/quorum` library [invokes the `storeraw` API](https://github.com/web3j/quorum/blob/v4.0.6/src/main/kotlin/org/web3j/quorum/tx/QuorumTransactionManager.kt#L52).
  - The Tessera Third Party API is enabled by default in 7nodes but not yet in [`quorum-maker` 2.6.2](https://github.com/synechron-finlabs/quorum-maker/tree/V2.6.2)
    that uses Tessera 0.8 but the legacy 0.7 configuration format; if you want to use `quorum-maker` you'll need to
    manually convert and overwrite the nodes' Tessera configuration after setting up the nodes but before running them
    (configuration examples for Linux and macOS with Docker are provided in [`examples/conf`](./examples/conf)).
  - Unfortunately there is no `quorum-maker` version that works with Tessera 0.9.x and Quorum 2.2.4 as of 20190704.
- The contract is compiled with `solcjs --bin --abi Counter.sol` using `solc@0.5.0` and both the ABI and bytecode are provided;
beware that later version of `solc` might produce bytecode that is invalid for your specific Quorum version;
the Quorum node log usually provides insight on why a transaction was unsuccessful (e.g. gas or invalid bytecode).