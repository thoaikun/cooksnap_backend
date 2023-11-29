package com.cooksnap.backend.services.servicesIplm;

import com.cooksnap.backend.domains.dto.ErrorResponseDto;
import com.cooksnap.backend.domains.dto.SuccessResponse;
import com.cooksnap.backend.domains.dto.requests.AddDishRequest;
import com.cooksnap.backend.domains.entity.Dish;
import com.cooksnap.backend.domains.entity.FavoriteDish;
import com.cooksnap.backend.domains.entity.FavoriteList;
import com.cooksnap.backend.domains.entity.User;
import com.cooksnap.backend.repositories.DishRepository;
import com.cooksnap.backend.repositories.FavoriteDishRepository;
import com.cooksnap.backend.repositories.FavoriteListRepository;
import com.cooksnap.backend.services.servicesInterface.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final FavoriteListRepository favoriteListRepository;
    private final FavoriteDishRepository favoriteDishRepository;
    public ResponseEntity<?> getFavoriteDish(int listId){
        try {
            List<Dish> list_dish = dishRepository.getFavoriteDish(listId);
            return ResponseEntity.ok().body(list_dish);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }


    public ResponseEntity<?> addFavoriteListByUserId(String name, Principal connectedUser){
        try {
            var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
            FavoriteList newFavoriteList = new FavoriteList();
            newFavoriteList.setListName(name);
            newFavoriteList.setUserId(user.getUserId());
            favoriteListRepository.save(newFavoriteList);
            return ResponseEntity.ok().body(new SuccessResponse("add success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> getFavoriteListByUserId(Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        try{
            List<FavoriteList> favoriteLists = favoriteListRepository.findByUserId(user.getUserId());
            return ResponseEntity.ok().body(favoriteLists);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> addDishToFavoriteList(AddDishRequest request, Principal connectedUser){
        try {
            Dish dish =  dishRepository.findByAbout(request.getAboutDish());
            if (dish == null){
                Dish newDish = new Dish();
                newDish.setAbout(request.getAboutDish());
                dishRepository.save(newDish);
                dish = dishRepository.findByAbout(request.getAboutDish());
            }


            FavoriteDish favoriteDish = new FavoriteDish();
            favoriteDish.setFavoriteListId(request.getListId());
            favoriteDish.setDishId(dish.getDish_id());

            favoriteDishRepository.save(favoriteDish);


            return ResponseEntity.ok().body(new SuccessResponse("add success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }

    public ResponseEntity<?> deleteDishToFavoriteList(AddDishRequest request, Principal connectedUser){
        try {
            favoriteDishRepository.deleteByDishIdAndFavoriteListId(request.getDishId(), request.getListId());
            return ResponseEntity.ok().body(new SuccessResponse("delete success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }


    public ResponseEntity<?> deleteFavorite(int listId){
        try{
            favoriteDishRepository.deleteByFavoriteListId(listId);
            favoriteListRepository.deleteById(listId);
            return ResponseEntity.ok().body(new SuccessResponse("delete success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponseDto("something error"));
        }
    }
}
