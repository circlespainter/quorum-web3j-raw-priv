var QM_PORT = 22000;

module.exports = {
  networks: {
    development: {
      host: "10.50.0.5",
      port: QM_PORT,
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000,
    },
    nodeone: {
      host: "10.50.0.2",
      port: QM_PORT,
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000,
    },
    nodetwo: {
      host: "10.50.0.3",
      port: QM_PORT,
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000,
    },
    nodethree: {
      host: "10.50.0.4",
      port: QM_PORT,
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000,
    },
    nodefour: {
      host: "10.50.0.5",
      port: QM_PORT,
      network_id: "*", // Match any network id
      gasPrice: 0,
      gas: 4500000,
    }
  }
};
