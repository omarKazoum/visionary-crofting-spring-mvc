const CARD_KEY="card";
const cardModal=document.querySelector("#card-modal");
const cardModalBody=cardModal.querySelector(".modal-body");

const loadCardFromLocalStorage=()=>{
    localStorage.setItem(CARD_KEY,JSON.stringify(cardContents));
}
var cardContents=JSON.parse(localStorage.getItem(CARD_KEY)) ||{items:[]};
const saveChangesToCardInLocalStorage=()=>{
    localStorage.setItem(CARD_KEY,JSON.stringify(cardContents));
}
const addProductToCard=(productId,unitPrice,title)=>{
    //if the product already exists in the card
    if(isProductAlreadyExistsINCard(productId)){
        //TODO:: just pen the card popup
        console.log("product already exits in the card")
    }else{
        cardContents.items.push({productId:productId,quantity:1,unitPrice:unitPrice,productTitle:title})
        console.log("successfully added product to card")
    }
    saveChangesToCardInLocalStorage();
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
const showCard=()=>{
    $("#card-modal").modal("show");
    console.log("card clicked")
    //let's fill data from card to modal
    cardModalBody.innerHTML="";
    cardContents.items.forEach(item=>{
        const itemHtml=`
                    <div class="form-group product-1 w-100 d-flex justify-content-between">
                        <label class="w-50" for="product-${item.productId}">${item.productTitle}</label>
                        <input class="w-25 product-quantity-input" type="number" name="quantity" data-product-id="${item.productId}" id="product-${item.productId}" value="${item.quantity}">
                    </div>`;
        cardModalBody.innerHTML+=itemHtml;
    })

}
document.querySelector("#show-card-menu").addEventListener("click",showCard);
const saveDraftCard=()=>{
    cardModalBody.querySelectorAll(".product-quantity-input").forEach(input=> {
        cardContents.items.filter(item => item.productId===input.dataset.productId)[0].quantity=input.value;
    });
    saveChangesToCardInLocalStorage();

}
const createOrderCard=()=>{

}
const clearCard=()=>{
    cardContents=null;
    saveChangesToCardInLocalStorage();
}

//init
loadCardFromLocalStorage();
