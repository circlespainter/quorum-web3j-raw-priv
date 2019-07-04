package com.dtcs.demo.dlt.quorumweb3j.rawpriv

import org.web3j.crypto.WalletUtils
import org.web3j.quorum.tx.QuorumTransactionManager
import java.io.File
import java.math.BigInteger

fun main() {
    val qs = QuorumService(PRIVATE_FOR_FROM_NODE_PUBK)

    val tmpDir = System.getProperty("java.io.tmpdir")
    val fn = WalletUtils.generateNewWalletFile("pwd", File(tmpDir))
    val filePath = "$tmpDir/$fn"
    File(filePath).deleteOnExit()

    val wallet = WalletUtils.loadCredentials("pwd", filePath)

    val qrtxm = QuorumTransactionManager(
        qs.quorum,
        wallet,
        PRIVATE_FOR_FROM_NODE_PUBK,
        listOf(PRIVATE_FOR_TO_NODE_PUBK),
        qs.enclave,
        DEFAULT_MAX_RETRY,
        DEFAULT_SLEEP_DURATION_IN_MILLIS
    )

    val counter = Counter.load(CONTRACT_ADDR, qs.quorum, qrtxm, BigInteger.valueOf(0), DEFAULT_GAS_LIMIT)
    val sendTransaction = counter.inc().send()

    val hash = sendTransaction.transactionHash
    println("TX HASH: $hash")

    val res = counter.count.send()
    println("COUNTER: $res")

    qs.shutdown()
}