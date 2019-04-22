a = eth.accounts[0]
web3.eth.defaultAccount = a;

// abi and bytecode generated from Counter.sol:
// > solcjs --bin --abi Counter.sol
var abi = [{"constant":false,"inputs":[],"name":"inc","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"getCount","outputs":[{"name":"","type":"int256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"dec","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"}]

var bytecode = "0x60806040526000805534801561001457600080fd5b50610101806100246000396000f3fe6080604052600436106053576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063371303c0146058578063a87d942c14606c578063b3bcfa82146094575b600080fd5b348015606357600080fd5b50606a60a8565b005b348015607757600080fd5b50607e60ba565b6040518082815260200191505060405180910390f35b348015609f57600080fd5b5060a660c3565b005b60016000808282540192505081905550565b60008054905090565b6001600080828254039250508190555056fea165627a7a72305820d8d478c82208c4fd1f9f314a5d7c4ecaa169e28a521208e4a9a989f196542f800029"

var simpleContract = web3.eth.contract(abi);
var simple = simpleContract.new(42, {from:web3.eth.accounts[0], data: bytecode, gas: 0x47b760, privateFor: ["QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc="]}, function(e, contract) {
	if (e) {
		console.log("err creating contract", e);
	} else {
		if (!contract.address) {
			console.log("Contract transaction send: TransactionHash: " + contract.transactionHash + " waiting to be mined...");
		} else {
			console.log("Contract mined! Address: " + contract.address);
			console.log(contract);
		}
	}
});
