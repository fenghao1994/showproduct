package com.shfound.showproduct.controller;

import com.shfound.showproduct.controller.result.SuccessResult;
import com.shfound.showproduct.controller.result.VoteClientResult;
import com.shfound.showproduct.controller.result.VoteResult;
import com.shfound.showproduct.model.VoteModel;
import com.shfound.showproduct.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @RequestMapping(value = "/create/vote", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult> createVote(@RequestParam("wxId")String wxId, @RequestParam("prodId") int prodId, @RequestParam("prodLimit")int prodLimit) {
        VoteModel voteModel = new VoteModel();
        voteModel.setWxId(wxId);
        voteModel.setProdId(prodId);
        voteModel.setProdLimit(prodLimit);

        boolean isSuccess = voteService.createVote(voteModel);
        SuccessResult successResult = new SuccessResult();
        if (isSuccess) {
            successResult.setCode(1000);
            successResult.setMessage("投票成功");
        } else {
            successResult.setCode(1001);
            successResult.setMessage("投票失败");
        }
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/getVote", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<VoteResult>> getVote(@RequestParam("prodId") int proId) {
        VoteResult allVote = voteService.getAllVote(proId);
        SuccessResult successResult = new SuccessResult();
        successResult.setCode(1000);
        successResult.setMessage("响应成功");
        successResult.setDate(allVote);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/client/getVote", method = RequestMethod.POST)
    public ResponseEntity<SuccessResult<VoteClientResult>> getClientVote(@RequestParam("wxId")String wxId) {
        List<VoteClientResult> clientVote = voteService.getClientVote(wxId);
        SuccessResult successResult = new SuccessResult();
        successResult.setCode(1000);
        successResult.setDate(clientVote);
        return new ResponseEntity<>(successResult, HttpStatus.OK);
    }
}



