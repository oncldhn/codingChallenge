Algorithm

To handle addding transaction and getting statistics 
in O(1) time and memory,

- Each valid transaction is aggregated to a tuple related to its second value of transaction time
  e.g  transaction occured in 17:30:45 is stored in 45th tuple.
- If the previous stored info is outdated then the existing data cleared and new transaction info is stored
- To gather statistics all 60 tuples are reduced so the time complexity is constant
