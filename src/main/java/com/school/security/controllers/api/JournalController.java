package com.school.security.controllers.api;

import com.school.security.dtos.requests.JournalReqDto;
import com.school.security.dtos.responses.JournalResDto;
import com.school.security.services.contracts.JournalService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journals")
@CrossOrigin(
        origins = {
            "http://localhost:3000",
            "http://192.168.1.133:3000/",
            "https://mfamaintenance.netlify.app/"
        })
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping
    public List<JournalResDto> findAll() {
        return this.journalService.findAll();
    }

    @GetMapping("/{id}")
    public JournalResDto findById(@PathVariable Long id) {
        return this.journalService.findById(id);
    }

    @PostMapping
    public JournalResDto save(@RequestBody JournalReqDto toSave) {
        return this.journalService.createOrUpdate(toSave);
    }

    @DeleteMapping("/{id}")
    public JournalResDto delete(@PathVariable Long id) {
        return this.journalService.deleteById(id);
    }
}
