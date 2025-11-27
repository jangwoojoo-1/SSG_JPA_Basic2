import httpRequester from "@/libs/httpRequester"; // ①

// 장바구니 상품 목록 조회
export const getItems = () => { // ②
    return httpRequester.get("/lp/api/cart/items").catch(e => e.response);
};

// 장바구니 상품 추가
export const addItem = (itemId) => { // ③
    return httpRequester.post("/lp/api/carts", { itemId }).catch(e => e.response);
};

// 장바구니에서 상품 삭제
export const removeItem = (itemId) => { // ④
    return httpRequester.delete(`/lp/api/cart/items/${itemId}`).catch(e => e.response);
};

// 장바구니 전체 삭제
export const removeAllItem = () => {
    return httpRequester.delete("/lp/api/cart/items").catch(e => e.response);
}