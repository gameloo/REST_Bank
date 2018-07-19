package com.program.controller;

import com.program.entity.Client;
import com.program.entity.History;
import com.program.entity.RequestBodyAddClient;
import com.program.repository.ClientRepository;
import com.program.repository.GroupRepository;
import com.program.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private GroupRepository groupRepository;


    @RequestMapping(value = "/get/all", method = RequestMethod.GET)                 //Вернуть всех клиентов
    @ResponseBody
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)            //Вернуть клиента по ID
    @ResponseBody
    public Client getClient(@PathVariable("id") long clientID) {
        return clientRepository.findById(clientID).get();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)    //Удалить клиента по ID
    @ResponseBody
    public void deleteClient(@PathVariable long id) {
        for (History history: clientRepository.findById(id).get().getHistory())
            historyRepository.delete(history);
        clientRepository.delete(clientRepository.findById(id).get());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)            //Добавить нового клиента
    @ResponseBody
    public RequestBodyAddClient saveClient(@RequestBody RequestBodyAddClient body) {
        Client newClient = clientRepository.saveAndFlush(body.getClient());
        History history = new History();
        history.setClient(newClient);
        history.setDate(new Date());
        history.setGroup(
                groupRepository.findById(
                        body.getGroupID()
                ).get()
        );
        historyRepository.saveAndFlush(history);
        return body;
    }

    @RequestMapping(value = "/get/history/{id}", method = RequestMethod.GET)    //Вернуть историю клиента по ID
    @ResponseBody
    public ArrayList<History> getHistoryClient(@PathVariable long id) {
        ArrayList<History> histories = new ArrayList<History>();
        for (History h: getClient(id).getHistory()){
            histories.add(historyRepository.findById(h.getId()).get());
        }
        return histories;
    }


}
