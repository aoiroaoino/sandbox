use crate::SortOrder;
use rand::distributions::Standard;
use rand::{Rng, SeedableRng};
use rand_pcg::Pcg64Mcg;

pub fn new_u32_vec(n: usize) -> Vec<u32> {
    let rng = Pcg64Mcg::from_seed([0; 16]);

    // let mut v = Vec::with_capacity(n);
    // for _ in 0..n {
    //     v.push(rng.sample(&Standard));
    // }
    // v

    rng.sample_iter(&Standard).take(n).collect()
}

pub fn is_sorted_ascending<T: Ord>(x: &[T]) -> bool {
    x.windows(2).all(|pair| pair[0] <= pair[1])
}

pub fn is_sorted_descending<T: Ord>(x: &[T]) -> bool {
    x.windows(2).all(|pair| pair[0] >= pair[1])
}

pub fn is_sorted<T: Ord>(x: &[T], order_by: &SortOrder) -> bool {
    match *order_by {
        SortOrder::Ascending => is_sorted_ascending(&x),
        SortOrder::Descending => is_sorted_descending(&x),
    }
}
