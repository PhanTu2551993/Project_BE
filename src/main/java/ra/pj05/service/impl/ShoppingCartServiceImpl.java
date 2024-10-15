package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.pj05.exception.ChangeException;
import ra.pj05.model.dto.request.ShoppingCartRequest;
import ra.pj05.model.entity.CartItem;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.Users;
import ra.pj05.repository.IShoppingCartRepository;
import ra.pj05.service.IShoppingCartService;
import ra.pj05.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Override
    public ShoppingCart findShoppingCartByUser(Users user) {
        return shoppingCartRepository.findShoppingCartByUser(user);
    }
}
