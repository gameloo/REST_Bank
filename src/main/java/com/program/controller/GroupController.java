package com.program.controller;

import com.program.entity.Group;
import com.program.entity.History;
import com.program.repository.GroupRepository;
import com.program.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/group")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @RequestMapping(value = "/get", method = RequestMethod.GET)                  //Вернуть все группы риска
    @ResponseBody
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)             //Добавить новую группу
    @ResponseBody
    public Group saveClient(@RequestBody Group group) {
        return groupRepository.saveAndFlush(group);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)    //Удалить клиента по ID
    @ResponseBody
    public void deleteGroup(@PathVariable long id) {
        for (History history: groupRepository.findById(id).get().getHistory())
            historyRepository.delete(history);
        groupRepository.delete(groupRepository.findById(id).get());
    }

}
