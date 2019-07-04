package com.dtcs.demo.dlt.quorumweb3j.rawpriv

import java.math.BigInteger

internal const val CONTRACT_ADDR = "0x36d15cc4f755c74d347376876ebc6730b4243dfa" // Private for node 1 and 2
internal const val PRIVATE_FOR_FROM_NODE_PUBK = "heAEyfibP4SXoGed0vRuTBZF7GRqkeHeWLd8/CqZ4yY="
internal const val PRIVATE_FOR_TO_NODE_PUBK = "5FW5A3vlAMUzmTfE0n2PwX7zk7bLKFdvhM8uKpHQUmI="

internal const val QUORUM_FROM_NODE_URL = "http://localhost:22000"
internal const val TESSERA_URL = "http://localhost"
internal const val TESSERA_PORT = 9081

internal const val DEFAULT_SLEEP_DURATION_IN_MILLIS = 2000L
internal const val DEFAULT_MAX_RETRY = 30

internal val DEFAULT_GAS_LIMIT = BigInteger.valueOf(100000)