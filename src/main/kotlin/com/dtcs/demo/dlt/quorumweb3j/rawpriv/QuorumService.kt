package com.dtcs.demo.dlt.quorumweb3j.rawpriv

import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.Function
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.EthCall
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.http.HttpService
import org.web3j.quorum.Quorum
import org.web3j.quorum.enclave.Tessera
import org.web3j.quorum.enclave.protocol.EnclaveService
import org.web3j.quorum.tx.QuorumTransactionManager
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

internal class QuorumService(
    private val fromNodePK: String,
    quorumUrl: String = QUORUM_FROM_NODE_URL,
    tesseraUrl: String = TESSERA_URL,
    tesseraPort: Int = TESSERA_PORT
) {

    fun signAndSendFunctionCallTx(
        creds: Credentials,
        contractAddr: String,

        func: Function,
        nonce: BigInteger,
        gasLimit: BigInteger,
        vararg privateFor: String
    ): CompletableFuture<EthSendTransaction> {

        val encodedFunc = FunctionEncoder.encode(func)
        val rawTx = RawTransaction.createTransaction(nonce, BigInteger.ZERO, gasLimit, contractAddr, encodedFunc)

        return if (privateFor.isNotEmpty()) {
            // quorum.ethSendRawPrivateTransaction(hexEncodedSignedRawTx, privateFor.toList())
            val qrtxm = QuorumTransactionManager(quorum, creds, fromNodePK, privateFor.asList(), enclave)
            CompletableFuture.supplyAsync { qrtxm.signAndSend(rawTx) }
        } else {
            val signedRawTx = TransactionEncoder.signMessage(rawTx, creds)
            val hexEncodedSignedRawTx = Numeric.toHexString(signedRawTx)
            quorum.ethSendRawTransaction(hexEncodedSignedRawTx).sendAsync()
        }
    }

    fun call(from: String, func: Function, contractAddr: String): CompletableFuture<EthCall> =
        quorum.ethCall(
            Transaction.createEthCallTransaction(from, contractAddr, FunctionEncoder.encode(func)),
            DefaultBlockParameterName.LATEST
        ).sendAsync()

    fun shutdown() {
        quorum.shutdown()
    }

    private val web3Service = HttpService(quorumUrl)
    internal val quorum = Quorum.build(web3Service)
    private val enclaveService = EnclaveService(tesseraUrl, tesseraPort)
    internal val enclave = Tessera(enclaveService, quorum)
}