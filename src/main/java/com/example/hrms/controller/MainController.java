package com.example.hrms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hrms.dto.AdminInfoDto;
import com.example.hrms.dto.EnquiryDto;
import com.example.hrms.dto.UserDto;
import com.example.hrms.model.AdminInfo;
import com.example.hrms.model.Enquiry;
import com.example.hrms.model.JobInfo;
import com.example.hrms.model.User;
import com.example.hrms.repo.AdminInfoRepo;
import com.example.hrms.repo.EnquiryRepo;
import com.example.hrms.repo.JobInfoRepo;
import com.example.hrms.repo.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired
	EnquiryRepo erepo;
	@Autowired
	UserRepo urepo;
	@Autowired
	AdminInfoRepo airepo;
	@Autowired
	JobInfoRepo jrepo;

	@GetMapping("/")
	public String showIndex(Model model) {
		List<JobInfo> jobs = jrepo.findAll();
		model.addAttribute("jobs", jobs);
		return "index";
	}

	@GetMapping("/aboutus")
	public String showAboutUs() {
		return "aboutus";
	}

	@GetMapping("/registration")
	public String showRegistration(Model model) {
		UserDto udto = new UserDto();
		model.addAttribute("udto", udto);
		return "registration";
	}

	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}

	@GetMapping("/joinus")
	public String showJoinUs() {
		return "joinus";
	}

	@GetMapping("/jobs")
	public String showJobs(Model model) {
		List<JobInfo> jobs = jrepo.findAll();
		model.addAttribute("jobs", jobs);
		return "jobs";
	}

	@PostMapping("/login")
	public String showUserDashboard(@RequestParam String emailaddress, @RequestParam String password, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Optional<User> user = urepo.findByEmailaddressAndPassword(emailaddress, password);
		if (user.isPresent()) {
			session.setAttribute("user", user.get());
			return "redirect:/user/userdash";
		}
		redirectAttributes.addFlashAttribute("msg", "Invalid email address or password. Please try again.");
		return "redirect:/login";
	}

	@GetMapping("/contactus")
	public String showContactUs(Model model) {
		EnquiryDto edto = new EnquiryDto();
		model.addAttribute("edto", edto);
		return "contactus";
	}

	@PostMapping("/contactus")
	public String saveEnquiry(@ModelAttribute EnquiryDto edto, RedirectAttributes attrib) {
		Enquiry enq = new Enquiry();
		enq.setName(edto.getName());
		enq.setAddress(edto.getAddress());
		enq.setContactno(edto.getContactno());
		enq.setEnquirytext(edto.getEnquirytext());
		erepo.save(enq);
		attrib.addFlashAttribute("msg", "Your enquiry has been received successfully! Our team will get back to you shortly.");
		return "redirect:/contactus";
	}

	@PostMapping("/registration")
	public String saveJobSeeker(@ModelAttribute User udto, RedirectAttributes attrib) {
		User user = new User();
		user.setName(udto.getName());
		user.setGender(udto.getGender());
		user.setContactno(udto.getContactno());
		user.setEmailaddress(udto.getEmailaddress());
		user.setPassword(udto.getPassword());
		user.setQualification(udto.getQualification());
		user.setExperience(udto.getExperience());
		user.setKeyskill(udto.getKeyskill());
		user.setAddress(udto.getAddress());
		urepo.save(user);
		attrib.addFlashAttribute("msg", "Candidate registered successfully! Please sign in to access your dashboard.");
		return "redirect:/registration";
	}

	@GetMapping("/adminlogin")
	public String showAdminLogin(Model model) {
		AdminInfoDto aidto = new AdminInfoDto();
		model.addAttribute("aidto", aidto);
		return "adminlogin";
	}

	@PostMapping("/adminlogin")
	public String adminLogin(@ModelAttribute AdminInfoDto aidto,
	                         RedirectAttributes attrib, HttpSession session) {
		String adminid = aidto.getAdminid();
		String password = aidto.getPassword();
		try {
			AdminInfo admin = airepo.findById(adminid).get();
			if (admin.getPassword().equals(password)) {
				session.setAttribute("admin", admin);
				return "redirect:/admin/admindashboard";
			} else {
				attrib.addFlashAttribute("msg", "Invalid Admin ID or Password. Please try again.");
				return "redirect:/adminlogin";
			}
		} catch (Exception e) {
			attrib.addFlashAttribute("msg", "Invalid Admin ID or Password. Please try again.");
			return "redirect:/adminlogin";
		}
	}
}


