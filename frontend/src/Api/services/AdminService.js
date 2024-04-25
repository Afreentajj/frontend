import axios from '../axios'

const getCampaigns = async() => {
    try{
        const response = await axios.get('/api/campaigns/mainAdminDash'
        
        )
    }
    catch(error){
        console.err(error)
    }
}

const AdminService ={
getCampaigns

}
export default AdminService