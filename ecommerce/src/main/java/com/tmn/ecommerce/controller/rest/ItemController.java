package com.tmn.ecommerce.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmn.ecommerce.dto.ItemDto;
import com.tmn.ecommerce.dto.ItemResponse;
import com.tmn.ecommerce.entity.Item;
import com.tmn.ecommerce.service.ItemService;

@RestController
@RequestMapping(path = "/api/item")
@CrossOrigin(origins = "http://localhost:4210")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<ItemResponse> getAllItems(){
		ItemResponse resp = new ItemResponse();
		try {
			resp.setStatus("200");
			resp.setMessage("Item List");
			resp.setOblist(itemService.getAllItems());
		} catch (Exception e) {
			resp.setStatus("500");
			resp.setMessage(e.getMessage());
		}	
		return new ResponseEntity<ItemResponse>(resp,HttpStatus.OK);
	}
	
	@PostMapping(produces = "application/json")
	public ResponseEntity<ItemResponse> createItem(@RequestBody ItemDto dto) {
		ItemResponse resp = new ItemResponse();
		try {
			if(itemService.existsByname(dto.getName())) {
				resp.setStatus("400");
				resp.setMessage("Item Already Exists!");
			} else {
				this.itemService.createItem(dto);
				resp.setStatus("200");
				resp.setMessage("Item Saved Successfully!");
			}
		} catch (Exception e) {
			resp.setStatus("500");
			resp.setMessage(e.getMessage());
		}
			//resp.setOblist(itemService.getAllItems());
		//this.itemService.createItem(dto);
		return new ResponseEntity<ItemResponse>(resp,HttpStatus.OK);
	}
	
	@PostMapping(path = "/{itemcode}",produces = "application/json")
	public ResponseEntity<ItemResponse> getItemByItemcode(@PathVariable("itemcode") String itemcode){
		ItemResponse resp = new ItemResponse();
		try {
			Item item = this.itemService.getItemByItemcode(itemcode);
			if (item==null) {
				resp.setStatus("400");
				resp.setMessage("Your item not found!");
			} else {
				resp.setStatus("200");
				resp.setMessage("This is item code");
				resp.setItem(item);
			}
		} catch (Exception e) {
			resp.setStatus("500");
			resp.setMessage(e.getMessage());
		}
		return new ResponseEntity<ItemResponse>(resp,HttpStatus.OK);
	}
	
//	public ResponseEntity<Response> createItem(@RequestParam("myFile") MultipartFile file,
//												@RequestParam("item") String item) throws IOException{
//		
//		Item itemdto = new ObjectMapper().readValue(item, Item.class);
//		itemdto.setPicByte(file.getBytes());
//		itemdto.setImageName(file.getOriginalFilename());
//		Item dbItem = itemService.createNewItem(dbItem);
//		if (dbItem != null) {
//			return new ResponseEntity<Response>(new Response("Item Saved Successfully"),HttpStatus.OK);
//		}
//		
//	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<ItemResponse> updateItem(@RequestBody ItemDto dto) {
		ItemResponse resp = new ItemResponse();
		try {
			if (itemService.existsByname(dto.getName())!=null) {
				resp.setStatus("400");
				resp.setMessage("Item Already Exists!");
			} else {
				this.itemService.createItem(dto);
				resp.setStatus("200");
				resp.setMessage("Item Updated Successfully!");
			}
			//resp.setOblist(itemService.getAll);
		} catch (Exception e) {
			resp.setStatus("500");
			resp.setMessage(e.getMessage());
		}
		return new ResponseEntity<ItemResponse>(resp,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<ItemResponse> deleteItem(@PathVariable("id") Long id) {
		ItemResponse resp = new ItemResponse();
		try {
			this.itemService.deleteItemById(id);
			resp.setStatus("200");
			resp.setMessage("Category Deleted Successfully");
		} catch (Exception e) {
			resp.setStatus("500");
			resp.setMessage(e.getMessage());
		}
		return new ResponseEntity<ItemResponse>(resp,HttpStatus.OK);
	}
}
