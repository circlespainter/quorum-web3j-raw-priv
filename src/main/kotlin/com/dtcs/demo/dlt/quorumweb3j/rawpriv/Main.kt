package com.dtcs.demo.dlt.quorumweb3j.rawpriv

import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Int
import org.web3j.crypto.WalletUtils
import java.io.File
import java.math.BigInteger

fun main(args: Array<String>) {
    val qs = QuorumService(PRIVATE_FOR_FROM_NODE_PUBK)

    val tmpDir = System.getProperty("java.io.tmpdir")
    val fn = WalletUtils.generateNewWalletFile("pwd", File(tmpDir))
    val filePath = "$tmpDir/$fn"
    File(filePath).deleteOnExit()

    val wallet = WalletUtils.loadCredentials("pwd", filePath)
    val nonce = BigInteger.ZERO // Always new account
    val gasLimit = BigInteger.valueOf(100000)
    val func = Function("inc", emptyList(), emptyList())

    val sendTransaction = qs.signAndSendFunctionCallTx(
        wallet,
        CONTRACT_ADDR,
        func,
        nonce,
        gasLimit,
        PRIVATE_FOR_TO_NODE_PUBK
    ).get()

    val hash = sendTransaction.transactionHash
    println("TX HASH: $hash")

    if (sendTransaction.error != null)
        println("TX ERROR: ${sendTransaction.error.message}")

    val queryFunc = Function("getCount", emptyList(), listOf(object : TypeReference<Int>(){}))
    val res = qs.call(wallet.address, queryFunc, CONTRACT_ADDR).get()

    if (res.error != null)
        println("QUERY ERROR: ${res.error.message}")
    else
        println("COUNTER: ${res.value}")

    qs.shutdown()
}