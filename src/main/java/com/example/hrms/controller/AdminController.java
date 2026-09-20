package com.example.hrms.controller;




import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.hrms.dto.JobInfoDto;
import com.example.hrms.model.AdminInfo;
import com.example.hrms.model.AppliedJob;
import com.example.hrms.model.Enquiry;
import com.example.hrms.model.JobInfo;
import com.example.hrms.model.Response;
import com.example.hrms.model.User;
import com.example.hrms.repo.AdminInfoRepo;
import com.example.hrms.repo.AppliedJobRepo;
import com.example.hrms.repo.EnquiryRepo;
import com.example.hrms.repo.JobInfoRepo;
import com.example.hrms.repo.ResponseRepo;
import com.example.hrms.repo.UserRepo;

import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	@Autowired
	UserRepo urepo;
   @Autowired
	EnquiryRepo enrepo;
    @Autowired
    JobInfoRepo jrepo;
    @Autowired
    AdminInfoRepo airepo;
    @Autowired
    ResponseRepo rrepo;
    @Autowired
    AppliedJobRepo ajrepo;
   

	
	@GetMapping("admin/admindashboard")
	public String showAdminDashboard(HttpSession session, Model model) {
		if (session.getAttribute("admin") == null) {
			return "redirect:/adminlogin";
		}
		model.addAttribute("usercounter", urepo.count());
		model.addAttribute("jobcounter", jrepo.count());
		model.addAttribute("enquirycounter", enrepo.count());
		model.addAttribute("appliedcounter", ajrepo.count());
		model.addAttribute("feedbackcounter", rrepo.count());

		List<JobInfo> allJobs = jrepo.findAll();
		model.addAttribute("recentJobs", allJobs.size() > 5 ? allJobs.subList(allJobs.size() - 5, allJobs.size()) : allJobs);

		List<AppliedJob> allApplicants = ajrepo.findAll();
		model.addAttribute("recentApplicants", allApplicants.size() > 5 ? allApplicants.subList(allApplicants.size() - 5, allApplicants.size()) : allApplicants);

		List<Enquiry> allEnquiries = enrepo.findAll();
		model.addAttribute("recentEnquiries", allEnquiries.size() > 5 ? allEnquiries.subList(allEnquiries.size() - 5, allEnquiries.size()) : allEnquiries);

		return "admin/admindashboard";
	}   @GetMapping("admin/jobseeker")
	public String viewUser(HttpSession session,Model model) {
		if(session.getAttribute("admin")==null) {
			return "redirect:/adminlogin";
		}
		List<User> user=urepo.findAll();
		model.addAttribute("users", user);
		return "admin/jobseeker";
	}
     
     @GetMapping("admin/enquiry")
    public String viewEnquiry(HttpSession session,Model model) {
    	if(session.getAttribute("admin")==null) {
    		return "redirect:/adminlogin";
    		
    	}
    	List<Enquiry> people=enrepo.findAll();
    	model.addAttribute("people",people);
    	return "admin/enquiry";
    	
    }
     @GetMapping("/admin/logout")
     public String logout(HttpSession session) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 session.invalidate();
    	 return "redirect:/adminlogin";
     }
     @GetMapping("/admin/postjob")
     public String showPostJob(Model model,HttpSession session) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 JobInfoDto jdto=new JobInfoDto();
    	 model.addAttribute("jdto",jdto);
    	 return "admin/postjob";
     }
     @PostMapping("/admin/postjob")
     public String saveJob(@ModelAttribute JobInfoDto jdto,HttpSession session ,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 JobInfo ji=new JobInfo();
    	 ji.setTitle(jdto.getTitle());
    	 ji.setDescription(jdto.getDescription());
    	 ji.setLocation(jdto.getLocation());
    	 ji.setSalary(jdto.getSalary());
    	 ji.setJobtype(jdto.getJobtype());
    	 ji.setLastdate(jdto.getLastdate());
    	 String posteddate=new Date().toString();
    	 ji.setPosteddate(posteddate);
    	 jrepo.save(ji);
    	 attrib.addFlashAttribute("msg","Job details is posed");
    	 return "redirect:/admin/postjob";
     }
     @GetMapping("/admin/postedjob")
     public String showPostedJob(HttpSession session, Model model) {

         if (session.getAttribute("admin") == null) {
             return "redirect:/adminlogin";
         }

         List<JobInfo>  job= jrepo.findAll();
         model.addAttribute("jobs", job);
         return "admin/postedjob";
     }
     @GetMapping("/admin/changeadminpwd")
     public String changeAdminPassword(HttpSession session) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 return "admin/changeadminpwd";
     }
     @PostMapping("/admin/changeadminpwd")
     public String changeAdminPwd(HttpSession session,HttpServletRequest request,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 String oldpassword=request.getParameter("oldpassword");
    	 String newpassword=request.getParameter("newpassword");
    	 String confirmpassword=request.getParameter("confirmpassword");
    	 if(!newpassword.equals(confirmpassword)) {
    		 attrib.addFlashAttribute("msg","Newassword and Confirmpassword are not matched ");
    		 return "redirect:/admin/changeadminpwd";
    	 }
    	 try {
    		 AdminInfo admin=(AdminInfo)session.getAttribute("admin");
    		 if(!admin.getPassword().equals(oldpassword)) {
    			 attrib.addFlashAttribute("msg","Newpassword is not matched ");
        		 return "redirect:/admin/changeadminpwd";
    		 }
    		 admin.setPassword(newpassword);
    		 airepo.save(admin);
    		 return "redirect:/admin/logout";
    	 }
    	 catch(Exception e) {
    		 attrib.addFlashAttribute("msg","AdminId not matched");
    		 return "redirect:/admin/changeadminpwd";
    		 
    	 }
     }
     @GetMapping("/admin/viewfeedback")
     public String viewFeedback(Model model ,HttpSession session) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 List<Response> feed=rrepo.findByResponsetype("feedback");
    	 model.addAttribute("feed",feed);
    	 return "admin/viewfeedback";
    	 
     }
    	 
     @GetMapping("/admin/viewcomplaint")
     public String viewComplaint(Model model ,HttpSession session) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 List<Response> comp=rrepo.findByResponsetype("complaint");
    	 model.addAttribute("comp",comp);
    	 return "admin/viewcomplaint";
    	 
     }
     @GetMapping("/admin/deletepeople")
     public String deleteEnquiry(HttpSession session , @RequestParam int id,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	 Enquiry e=enrepo.findById(id).get();
    	 enrepo.delete(e);
    	 attrib.addFlashAttribute("msg","Enquiry is delete successfully");
    	 return "redirect:/admin/enquiry";
     }
     @GetMapping("/admin/deletefeed")
     public String deleteFeedback(HttpSession session , @RequestParam int id,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	  Response r=rrepo.findById(id).get();
    	 rrepo.delete(r);
    	 attrib.addFlashAttribute("msg","Feedback is delete successfully");
    	 return "redirect:/admin/viewfeedback";
     }
     @GetMapping("/admin/deletecomp")
     public String deleteComplaint(HttpSession session , @RequestParam int id,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	  Response r=rrepo.findById(id).get();
    	 rrepo.delete(r);
    	 attrib.addFlashAttribute("msg","Complaint is delete successfully");
    	 return "redirect:/admin/viewcomplaint";
     }
     @GetMapping("/admin/deletejobs")
     public String deletePostedJobs(HttpSession session , @RequestParam int id,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	  JobInfo r=jrepo.findById(id).get();
    	 jrepo.delete(r);
    	 attrib.addFlashAttribute("msg","Posted Job is delete successfully");
    	 return "redirect:/admin/postedjob";
     }
     @GetMapping("/admin/deleteusers")
     public String deleteJobSeeker(HttpSession session , @RequestParam int id,RedirectAttributes attrib) {
    	 if(session.getAttribute("admin")==null) {
    		 return "redirect:/adminlogin";
    	 }
    	  User r=urepo.findById(id).get();
    	 urepo.delete(r);
    	 attrib.addFlashAttribute("msg","Posted Job is delete successfully");
    	 return "redirect:/admin/jobseeker";
     }
   @GetMapping("/admin/appliedjobs")
     public String viewAppiedJobs(HttpSession session ,Model model){
	   if(session.getAttribute("admin")==null) {
  		 return "redirect:/adminlogin";
  	 }
	   List<AppliedJob> aj=ajrepo.findAll();
	   model.addAttribute("aj", aj);
	   return "admin/appliedjobs";
    	 
     }
}
