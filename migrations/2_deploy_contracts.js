var Counter = artifacts.require("Counter");

const PUBLIC_KEYS = ["AtB3BxwsI1lcChZoAG7rtl2yM8GykMKIEXfbjqq0ki4=", "TIq8NdpCPRKpq+xBNAc+2aNpH3Nruk0ypQhOLvqOjRw=", "1GWAknPu7lkof7DYalz9t+ZHHejiPA1PaVoYHajxyl4="];

module.exports = function(deployer) {

  deployer.then(async () => {

    deployer.deploy(Counter, {privateFor: PUBLIC_KEYS});
  })
};