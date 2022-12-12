const CARD_KEY="card";
const API_BASE_URL="http://localhost:9000";
const cardModal=document.querySelector("#card-modal");
const cardModalBody=cardModal.querySelector(".modal-body");

const loadCardFromLocalStorage=()=>{
    localStorage.setItem(CARD_KEY,JSON.stringify(cardContents));
}
var cardContents=JSON.parse(localStorage.getItem(CARD_KEY)) ||{items:[]};
const saveChangesToCartInLocalStorage=()=>{
    localStorage.setItem(CARD_KEY,JSON.stringify(cardContents));
}
const addProductToCard=(productId,unitPrice,title)=>{
    //if the product already exists in the card
    if(isProductAlreadyExistsINCard(productId)){
        //TODO:: just pen the card popup
        console.log("product already exits in the cart")
    }else{
        cardContents.items.push({productId:productId,quantity:1,unitPrice:unitPrice,productTitle:title})
        console.log("successfully added product to cart")
    }
    saveChangesToCartInLocalStorage();
}
const isProductAlreadyExistsINCard=(productId)=>{
    return cardContents.items.filter(item=>item.productId==productId).length==1
}
const disableAddToCardBtn=(btn)=> {
    btn=recreateNode(btn)
    //btn.setAttribute("disabled","true")
    btn.innerText="Already in card!";
}

function recreateNode(el, withChildren) {
    let newEl=el.cloneNode(true);
    if (withChildren) {
        el.parentNode.replaceChild(newEl, el);
    }
    else {
        while (el.hasChildNodes()) newEl.appendChild(el.firstChild);
        el.parentNode.replaceChild(newEl, el);
    }
    return newEl;
}
document.querySelectorAll(".addToCardBtn").forEach(btn=>{
    if(isProductAlreadyExistsINCard(btn.dataset.productId)){
        disableAddToCardBtn(btn);
    }else
    btn.addEventListener("click",(e)=>{
        e.preventDefault();
        //if the add to cat btn is clicked
        let clickedBtn=e.target;
        let productId=clickedBtn.dataset.productId;
        let productUnitPrice=clickedBtn.dataset.productPrice;
        let productUnitTitle=clickedBtn.dataset.productTitle;
        console.log("added to card product "+productId)
        addProductToCard(productId,productUnitPrice,productUnitTitle)
        disableAddToCardBtn(clickedBtn)
    })
})
const removeItemBtnClicked=(e)=>{
    e.preventDefault();
    console.log("remove clicked")
    let productIdToRemove=e.target.dataset.productId;
    cardModalBody.removeChild(e.target.closest(".form-group"));
    cardContents.items=cardContents.items.filter(item=>item.productId!=productIdToRemove);
}
const showCard=()=>{
    $("#card-modal").modal("show");
    console.log("card clicked")
    //let's fill data from card to modal
    cardModalBody.innerHTML="";
    if(!cardContents.items || cardContents.items.length==0){
        const itemHtml = `
                        <b class="text-info text-center w-100">Your card is empty!</b>`;
        cardModalBody.innerHTML += itemHtml;
    }else {
        cardContents.items.forEach(item => {
            const itemHtml = `
                        <div class="form-group product-1 w-100 d-flex justify-content-between">
                            <label  class="w-50" for="product-${item.productId}">${item.productTitle}</label>
                            <input min="1" class="w-25 product-quantity-input" type="number" name="quantity" data-product-id="${item.productId}" id="product-${item.productId}" value="${item.quantity}">
                            <i class="w-25 fa-regular fa-trash-xmark card-btn-remove-item" data-product-id="${item.productId}" onclick="(e)=>{removeItemBtnClicked(e)}" > X </i>
                        </div>`;

            cardModalBody.innerHTML += itemHtml;

        })

    }

}
document.querySelector("#show-card-menu").addEventListener("click",showCard);
const saveDraftCard=()=>{
    cardModalBody.querySelectorAll(".product-quantity-input").forEach(input=> {
        cardContents.items.filter(item => item.productId===input.dataset.productId)[0].quantity=input.value;
    });
    saveChangesToCartInLocalStorage();

}
const clearCart=()=>{
    cardContents=null;
    saveChangesToCartInLocalStorage();
}
const createOrderCart=()=> {
    console.log("create order clicked")
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "clientId": 1,
        "items":
            cardContents.items.map(item=>{let res={"productId":item.productId,"quantity":item.quantity};
                return  res;
            })

    });
    console.log("raw data is ",raw)

    let requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch(API_BASE_URL+"/api/orders/", requestOptions)
        .then(result => {
            if(result.ok) {
                console.log("success")
                result.json().then(console.log)
                Swal.fire(
                    'Order created successfully !',
                    'success'
                )
            }else{
                console.error("error")
                result.json().then(console.log)
                Swal.fire(
                    'Order created successfully !',
                    'success'
                )
            }
        })
        .catch(error => console.log('error', error));
}
//init
loadCardFromLocalStorage();
