pragma solidity ^0.5.0;

contract Counter {
  int private count = 0;

  function inc() public {
    count += 1;
  }

  function dec() public {
    count -= 1;
  }

  function getCount() public view returns (int) {
    return count;
  }
}
