import axios from "axios"; // ①

// 장바구니 상품 목록 조회
export const getItems = () => { // ②
    return axios.get("/lp/api/cart/items").catch(e => e.response);
};

// 장바구니 상품 추가
export const addItem = (itemId) => { // ③
    return axios.post("/lp/api/carts", { itemId }).catch(e => e.response);
};

// 장바구니에서 상품 삭제
export const removeItem = (itemId) => { // ④
    return axios.delete(`/lp/api/cart/items/${itemId}`).catch(e => e.response);
};

// 장바구니 전체 삭제
export const removeAllItem = () => {
    return axios.delete("/lp/api/cart/items").catch(e => e.response);
}